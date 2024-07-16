package com.repaso.listaTareas.domain.usecase.dto;

import com.repaso.listaTareas.domain.usecase.dto.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private UUID id;
    private String description;
    private TaskStatus status;

}
