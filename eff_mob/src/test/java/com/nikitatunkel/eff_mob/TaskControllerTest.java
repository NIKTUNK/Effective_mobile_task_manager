package com.nikitatunkel.eff_mob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikitatunkel.eff_mob.model.Task;
import com.nikitatunkel.eff_mob.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1"),
                new Task(2L, "Task 2", "Description 2")
        );

        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tasks.size()))
                .andExpect(jsonPath("$[0].id").value(tasks.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(tasks.get(0).getTitle()))
                .andExpect(jsonPath("$[1].id").value(tasks.get(1).getId()))
                .andExpect(jsonPath("$[1].title").value(tasks.get(1).getTitle()));
    }

    @Test
    void testCreateTask() throws Exception {
        Task newTask = new Task(3L, "New Task", "New Description");

        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newTask.getId()))
                .andExpect(jsonPath("$.title").value(newTask.getTitle()))
                .andExpect(jsonPath("$.description").value(newTask.getDescription()));
    }

    @Test
    void testUpdateTask() throws Exception {
        Long taskId = 1L;
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description");

        Mockito.when(taskService.updateTask(Mockito.eq(taskId), Mockito.any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedTask.getId()))
                .andExpect(jsonPath("$.title").value(updatedTask.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedTask.getDescription()));
    }

    @Test
    void testDeleteTask() throws Exception {
        Long taskId = 1L;

        Mockito.doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isOk());
    }
}