package com.miniproject.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTitleRequest(
    @NotBlank(message = "Title is required")
    String title
) {}