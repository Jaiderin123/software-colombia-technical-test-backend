package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.entity;

import org.springframework.data.relational.core.mapping.Column;

public record WorkspaceWithUserRoleEntity(
        @Column("workspace_id")
        Long workspaceId,
        @Column("workspace_name")
        String workspaceName,
        @Column("role")
        String role
) {}
