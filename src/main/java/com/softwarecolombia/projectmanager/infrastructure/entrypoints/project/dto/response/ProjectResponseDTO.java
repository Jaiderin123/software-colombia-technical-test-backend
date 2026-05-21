package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.dto.response;

import java.time.LocalDateTime;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) { }
