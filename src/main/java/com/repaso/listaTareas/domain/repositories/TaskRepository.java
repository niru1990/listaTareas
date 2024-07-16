package com.repaso.listaTareas.domain.repositories;

import com.repaso.listaTareas.domain.entitys.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

}
