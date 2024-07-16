package com.repaso.listaTareas.domain.usecase;

import com.repaso.listaTareas.application.mapper.TaskDTOMapper;
import com.repaso.listaTareas.domain.entitys.TaskEntity;
import com.repaso.listaTareas.domain.repositories.TaskRepository;
import com.repaso.listaTareas.domain.usecase.dto.TaskDTO;
import com.repaso.listaTareas.domain.usecase.dto.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskUseCase {

    private final TaskRepository taskRepository;

    public TaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getTasks() {
        return TaskDTOMapper.fromEntities(taskRepository.findAll());
    }

    public TaskDTO getTaskById(UUID id) {
        Optional<TaskEntity> entity = this.taskRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RuntimeException();
        }
        return TaskDTOMapper.fromEntity(entity.get());
    }

    public synchronized TaskDTO createTask(TaskDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID());
        }
        dto.setStatus(TaskStatus.TODO);
        return TaskDTOMapper.fromEntity(this.taskRepository.save(TaskDTOMapper.toEntity(dto)));
    }

    public synchronized TaskDTO updateById(TaskDTO dto, UUID id) {
        Optional<TaskEntity> optionalEntity = this.taskRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new RuntimeException();
        }

        TaskEntity entity = optionalEntity.get();
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return TaskDTOMapper.fromEntity(this.taskRepository.save(entity));
    }

    public synchronized void delete(UUID id) {
        this.taskRepository.deleteById(id);
    }


}
