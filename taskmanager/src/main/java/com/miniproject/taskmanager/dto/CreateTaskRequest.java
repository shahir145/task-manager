package com.miniproject.taskmanager.dto;

import com.miniproject.taskmanager.model.Priority;
import com.miniproject.taskmanager.model.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskRequest(
    @NotBlank(message = "Title is required")
    String title,

    String description,

    @NotNull(message = "Status is required")
    Status status,

    @NotNull(message = "Priority is required")
    Priority priority,

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    LocalDateTime dueAt
) {}