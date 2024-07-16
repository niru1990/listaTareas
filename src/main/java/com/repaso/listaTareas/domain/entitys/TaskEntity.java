package com.repaso.listaTareas.domain.entitys;

import com.repaso.listaTareas.domain.usecase.dto.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(schema = "dbo", name = "task")
@Getter
@Setter
public class TaskEntity {

    @Id
    private UUID id;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

}
