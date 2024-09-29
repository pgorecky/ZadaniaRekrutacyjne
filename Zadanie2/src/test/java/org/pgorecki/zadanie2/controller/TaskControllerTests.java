package org.pgorecki.zadanie2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pgorecki.zadanie2.dto.AddTaskRequest;
import org.pgorecki.zadanie2.model.TaskStatus;
import org.pgorecki.zadanie2.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    private String taskId;

    private AddTaskRequest createTaskRequest() {
        return new AddTaskRequest("title", "desc", TaskStatus.SUBMITTED, LocalDate.now(), new ArrayList<>());
    }

    private AddTaskRequest secondTaskRequest() {
        return new AddTaskRequest("newTitle", "newDesc", TaskStatus.IN_PROGRESS, LocalDate.now(), new ArrayList<>());
    }

    @BeforeEach
    void shouldSuccessfullyCreateTask() throws Exception {
        AddTaskRequest addTaskRequest = createTaskRequest();

        //create new task
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tasks")
                        .content(objectMapper.writeValueAsString(addTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //check if task is successfully created
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tasks/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        taskId = String.valueOf(rootNode.get(0).get("id"));
    }

    @AfterEach
    void cleanAfterTest() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullyDeleteTask() throws Exception {
        //delete task
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/tasks/" + taskId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check if there is no task
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tasks/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldSuccessfullyUpdateTask() throws Exception {
        AddTaskRequest updateTaskRequest = secondTaskRequest();

        //update task
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/tasks/" + taskId)
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check if task is updated
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tasks/" + taskId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title", is(updateTaskRequest.title())))
                .andExpect(jsonPath("description", is(updateTaskRequest.description())))
                .andExpect(jsonPath("status", is(updateTaskRequest.status().toString())));
    }

    @Test
    void shouldSuccessfullyPartiallyUpdateTask() throws Exception {
        Map<String, String> updateTaskRequest = new HashMap<>();
        updateTaskRequest.put("title", "newTitle");
        updateTaskRequest.put("status", "IN_PROGRESS");

        //update task
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/tasks/" + taskId)
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check if task is updated
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/tasks/" + taskId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title", is("newTitle")))
                .andExpect(jsonPath("status", is("IN_PROGRESS")));
    }

    @Test
    void shouldSuccessfullyFilterTasks() throws Exception {
        AddTaskRequest addTaskRequest = secondTaskRequest();

        //create new task
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tasks")
                        .content(objectMapper.writeValueAsString(addTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //check if new task is properly filtered
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/api/tasks/all?title=%s&description=%s&status=%s&deadlineFrom=%s&deadlineTo=%s",
                                secondTaskRequest().title(),
                                secondTaskRequest().description(),
                                secondTaskRequest().status().toString(),
                                secondTaskRequest().deadline().toString(),
                                secondTaskRequest().deadline().toString()
                                ))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(secondTaskRequest().title())))
                .andExpect(jsonPath("$[0].description", is(secondTaskRequest().description())))
                .andExpect(jsonPath("$[0].status", is(secondTaskRequest().status().toString())))
                .andExpect(jsonPath("$[0].deadline", is(secondTaskRequest().deadline().toString())));
    }

}
