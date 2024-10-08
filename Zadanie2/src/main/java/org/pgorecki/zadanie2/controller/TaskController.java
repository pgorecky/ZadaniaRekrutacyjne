package org.pgorecki.zadanie2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.pgorecki.zadanie2.dto.AddTaskRequest;
import org.pgorecki.zadanie2.dto.TaskDto;
import org.pgorecki.zadanie2.dto.TaskSearchRequest;
import org.pgorecki.zadanie2.model.Task;
import org.pgorecki.zadanie2.model.TaskStatus;
import org.pgorecki.zadanie2.repository.TaskSearchDao;
import org.pgorecki.zadanie2.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskSearchDao taskSearchDao;
    private final ModelMapper modelMapper;

    private TaskDto convertToTaskDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public URI createTask(@Valid @RequestBody AddTaskRequest newTask) {
        Task createdTask = taskService.createTask(newTask);
        log.info("A new task has been created with id: {}", createdTask.getId());
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTask.getId())
                .toUri();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskInformation(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return convertToTaskDto(task);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        log.warn("Task with id: {} has been removed", id);
        return String.format("Task with id: %s deleted successfully", id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable Long id, @Valid @RequestBody AddTaskRequest updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        log.info("Task with id: {} has been updated", id);
        return convertToTaskDto(task);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TaskDto partiallyUpdateTask(@PathVariable Long id, @RequestBody AddTaskRequest updatedTask) {
        Task task = taskService.partiallyUpdateTask(id, updatedTask);
        log.info("Task with id: {} has been partially updated", id);
        return convertToTaskDto(task);
    }

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) LocalDate deadlineFrom,
            @RequestParam(required = false) LocalDate deadlineTo
            ) {
        TaskSearchRequest taskSearchRequest = new TaskSearchRequest(title, description, status, deadlineFrom, deadlineTo);

        List<Task> tasks = taskSearchDao.findByCriteria(taskSearchRequest.getCriteriaParams());

        return tasks.stream()
                .map(this::convertToTaskDto)
                .toList();
    }
}
