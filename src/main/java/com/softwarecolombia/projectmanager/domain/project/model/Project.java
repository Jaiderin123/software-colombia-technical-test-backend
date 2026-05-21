package com.softwarecolombia.projectmanager.domain.project.model;

import java.time.LocalDateTime;

public record Project(
        Long id,
        Long workspaceId,
        String name,
        String description,
        LocalDateTime createdAt
) {
    public Project(Long workspaceId, String name, String description){
        this(null, workspaceId, name, description, LocalDateTime.now());
    }
}
