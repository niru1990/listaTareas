package com.repaso.listaTareas.application.mapper;

import com.repaso.listaTareas.domain.entitys.TaskEntity;
import com.repaso.listaTareas.domain.usecase.dto.TaskDTO;

import java.util.List;

public class TaskDTOMapper {

    public static TaskDTO fromEntity(TaskEntity entity) {
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static TaskEntity toEntity(TaskDTO dto) {
        TaskEntity entity = new TaskEntity();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public static List<TaskDTO> fromEntities(List<TaskEntity> entities) {
        return entities.stream()
                .map(TaskDTOMapper::fromEntity)
                .toList();
    }

}
