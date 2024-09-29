package org.pgorecki.zadanie2.service;

import lombok.RequiredArgsConstructor;
import org.pgorecki.zadanie2.dto.AddTaskRequest;
import org.pgorecki.zadanie2.exception.ResourceNotFoundException;
import org.pgorecki.zadanie2.model.Task;
import org.pgorecki.zadanie2.model.TaskStatus;
import org.pgorecki.zadanie2.model.User;
import org.pgorecki.zadanie2.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task createTask(AddTaskRequest task) {
        Task newTask = mapAddTaskRequestToTask(task, new Task());

        return taskRepository.save(newTask);
    }

    public Task getTaskById(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
    }

    public void deleteTaskById(Long id) {
        Task taskToDelete = taskRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        taskRepository.deleteById(taskToDelete.getId());
    }

    public Task updateTask(Long id, AddTaskRequest updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

        mapAddTaskRequestToTask(updatedTask, existingTask);
        return taskRepository.save(existingTask);
    }

    private Task mapAddTaskRequestToTask(AddTaskRequest updatedTask, Task existingTask) {
        existingTask.setTitle(updatedTask.title());
        existingTask.setDescription(updatedTask.description());
        existingTask.setStatus(updatedTask.status() != null ? updatedTask.status() : TaskStatus.SUBMITTED);
        existingTask.setDeadline(updatedTask.deadline());

        if (updatedTask.assignedUsers() != null) {
            if (updatedTask.assignedUsers().isEmpty()) {
                existingTask.getAssignedUsers().clear();
            } else {
                List<User> assignedUsers = userService.getUsersByIds(updatedTask.assignedUsers());
                existingTask.setAssignedUsers(assignedUsers);
            }
        }

        return existingTask;
    }

    public Task partiallyUpdateTask(Long id, AddTaskRequest updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

        if (updatedTask.title() != null && !updatedTask.title().isBlank()) {
            existingTask.setTitle(updatedTask.title());
        }

        if (updatedTask.description() != null) {
            existingTask.setDescription(updatedTask.description());
        }

        if (updatedTask.status() != null) {
            existingTask.setStatus(updatedTask.status());
        }

        if (updatedTask.deadline() != null) {
            existingTask.setDeadline(updatedTask.deadline());
        }

        if (updatedTask.assignedUsers() != null) {
            List<User> assignedUsers = userService.getUsersByIds(updatedTask.assignedUsers());
            existingTask.setAssignedUsers(assignedUsers);
        }

        return taskRepository.save(existingTask);
    }
}
