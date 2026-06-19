package com.miniproject.taskmanager.dto;

import com.miniproject.taskmanager.model.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
    @NotNull(message = "Status is required")
    Status status
) {}