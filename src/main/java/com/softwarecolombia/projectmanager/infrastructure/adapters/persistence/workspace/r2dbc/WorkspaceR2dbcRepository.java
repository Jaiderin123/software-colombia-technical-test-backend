package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.r2dbc;

import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.entity.WorkspaceEntity;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.entity.WorkspaceWithUserRoleEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WorkspaceR2dbcRepository extends R2dbcRepository<WorkspaceEntity, Long> {
    @Query("""
        SELECT w.id AS workspace_id, w.name AS workspace_name, wu.role AS role
        FROM workspaces w
        INNER JOIN workspace_users wu ON w.id = wu.workspace_id
        WHERE wu.user_id = :userId
    """)
    Flux<WorkspaceWithUserRoleEntity> findWorkspacesWithRoleByUserId(Long userId);

    @Query("""
        SELECT wu.role AS role
        FROM workspaces w
        INNER JOIN workspace_users wu ON w.id = wu.workspace_id
        WHERE wu.user_id = :userId AND w.id = :workspaceId
    """)
    Mono<String> findWorkspaceUserRoleByUserIdAndWorkspaceId(Long userId, Long workspaceId);
}
