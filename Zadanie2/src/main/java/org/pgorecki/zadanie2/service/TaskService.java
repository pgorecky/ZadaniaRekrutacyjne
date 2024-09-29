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
        Task newTask = new Task();

        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus() != null ? task.getStatus() : TaskStatus.SUBMITTED);
        newTask.setDeadline(task.getDeadline());
        if (!task.getAssignedUsers().isEmpty()) {
            List<User> assignedUsers = userService.getUsersByIds(task.getAssignedUsers());
            newTask.setAssignedUsers(assignedUsers);
        }

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
        taskRepository.deleteById(id);
    }
}
