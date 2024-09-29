package org.pgorecki.zadanie2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pgorecki.zadanie2.model.User;
import org.pgorecki.zadanie2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private String userId;

    private User createUserRequest() {
        return User.builder()
                .firstName("user")
                .lastName("example")
                .email("example@mail.com")
                .build();
    }

    private User createSecondUserRequest() {
        return User.builder()
                .firstName("another")
                .lastName("person")
                .email("mail@mail.com")
                .build();
    }

    @BeforeEach
    void shouldSuccessfullyCreateUser() throws Exception {
        User addUserRequest = createUserRequest();

        //create new user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .content(objectMapper.writeValueAsString(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //check if user is successfully created
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(addUserRequest.getFirstName())))
                .andExpect(jsonPath("$[0].firstName", is(addUserRequest.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(addUserRequest.getLastName())))
                .andExpect(jsonPath("$[0].email", is(addUserRequest.getEmail())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        userId = String.valueOf(rootNode.get(0).get("id"));
    }

    @AfterEach
    void cleanAfterTest() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullyDeleteUser() throws Exception {
        //delete user
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check if there is no user
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldSuccessfullyFilterUsers() throws Exception {
        User addUserRequest = createSecondUserRequest();

        //add second user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .content(objectMapper.writeValueAsString(addUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //check if new user is properly filtered
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/api/users/all?firstName=%s&lastName=%s&%s", addUserRequest.getFirstName(), addUserRequest.getLastName(), addUserRequest.getEmail()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(addUserRequest.getFirstName())))
                .andExpect(jsonPath("$[0].firstName", is(addUserRequest.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(addUserRequest.getLastName())))
                .andExpect(jsonPath("$[0].email", is(addUserRequest.getEmail())));
    }
}
