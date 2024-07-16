package com.repaso.listaTareas.infraestrucutre.rest;

import com.repaso.listaTareas.domain.usecase.TaskUseCase;
import com.repaso.listaTareas.domain.usecase.dto.TaskDTO;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskUseCase taskUseCase;

    public TaskController(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @GetMapping
    public List<TaskDTO> getTasks() {
        return this.taskUseCase.getTasks();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO getTask(@PathVariable UUID id) {
        return this.taskUseCase.getTaskById(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO t) {
        return this.taskUseCase.createTask(t);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO t, @PathVariable("id") UUID id) {
        return this.taskUseCase.updateById(t, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable("id") UUID id) {
        this.taskUseCase.delete(id);
    }

}
