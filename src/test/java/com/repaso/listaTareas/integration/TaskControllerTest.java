package com.repaso.listaTareas.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.repaso.listaTareas.AbstractIntegrationTest;
import com.repaso.listaTareas.domain.repositories.TaskRepository;
import com.repaso.listaTareas.domain.usecase.TaskUseCase;
import com.repaso.listaTareas.domain.usecase.dto.TaskDTO;
import com.repaso.listaTareas.domain.usecase.dto.enums.TaskStatus;
import com.repaso.listaTareas.infraestrucutre.rest.TaskController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TaskControllerTest extends AbstractIntegrationTest {

    private final TaskController taskController;

    private final TaskUseCase taskUseCase;

    private final TaskRepository taskRepository;

    private final MockMvc mockMvc;

    private static final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build()
            .registerModule(new ParameterNamesModule());

    @Autowired
    public TaskControllerTest(TaskController taskController, TaskUseCase taskUseCase, TaskRepository taskRepository, MockMvc mockMvc) {
        this.taskController = taskController;
        this.taskUseCase = taskUseCase;
        this.taskRepository = taskRepository;
        this.mockMvc = mockMvc;
    }

    @Test
    public void getTasks() throws Exception {
        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getTask() throws Exception {
        mockMvc.perform(get("/task/7b6646e0-281b-4e2f-b421-ac4184284a61"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("7b6646e0-281b-4e2f-b421-ac4184284a61"))
                .andExpect(jsonPath("$.description").value("a1"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    public void createTask() throws Exception {

        TaskDTO data = new TaskDTO(UUID.fromString("99e01693-40c9-46e5-be58-8342bbc93ab1"), "a4",
                TaskStatus.TODO);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("99e01693-40c9-46e5-be58-8342bbc93ab1"))
                .andExpect(jsonPath("$.description").value("a4"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    public void updateTask() throws Exception {

        TaskDTO data = new TaskDTO(UUID.fromString("0dd0bba6-8e2b-488a-99c8-bb1fc86bd8af"),
                "NO PUEDO, ESTOY CASADO....", TaskStatus.BLOCKED);

        mockMvc.perform(put("/task/0dd0bba6-8e2b-488a-99c8-bb1fc86bd8af")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("0dd0bba6-8e2b-488a-99c8-bb1fc86bd8af"))
                .andExpect(jsonPath("$.description").value("NO PUEDO, ESTOY CASADO...."))
                .andExpect(jsonPath("$.status").value("BLOCKED"));
    }

    @Test
    public void deleteTask() throws Exception {
        var uuid = "4d100f6a-6d06-4635-aae9-a5ab98f327ae";
        Assertions.assertTrue(this.taskRepository.findById(UUID.fromString(uuid)).isPresent());
        mockMvc.perform(delete("/task/" + uuid)).andExpect(status().isOk());
        Assertions.assertTrue(this.taskRepository.findById(UUID.fromString(uuid)).isEmpty());
    }

}
