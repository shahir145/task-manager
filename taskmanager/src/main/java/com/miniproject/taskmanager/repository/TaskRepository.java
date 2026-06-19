package com.miniproject.taskmanager.repository;

import com.miniproject.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {}