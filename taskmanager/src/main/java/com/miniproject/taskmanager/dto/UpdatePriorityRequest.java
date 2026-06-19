package com.miniproject.taskmanager.dto;

import com.miniproject.taskmanager.model.Priority;
import jakarta.validation.constraints.NotNull;

public record UpdatePriorityRequest(
    @NotNull(message = "Priority is required")
    Priority priority
) {}