package org.pgorecki.zadanie2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.pgorecki.zadanie2.dto.AddTaskRequest;
import org.pgorecki.zadanie2.dto.TaskDto;
import org.pgorecki.zadanie2.model.Task;
import org.pgorecki.zadanie2.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
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
}
