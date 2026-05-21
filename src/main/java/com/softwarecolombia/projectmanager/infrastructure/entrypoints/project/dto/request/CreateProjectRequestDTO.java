package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequestDTO(
        @NotBlank(message = "Branch new name is required")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String name,
        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters")
        String description
) {
}
