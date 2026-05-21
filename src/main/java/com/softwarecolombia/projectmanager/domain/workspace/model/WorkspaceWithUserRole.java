package com.softwarecolombia.projectmanager.domain.workspace.model;

public record WorkspaceWithUserRole(
        Long workspaceId,
        String workspaceName,
        String role
) {}
