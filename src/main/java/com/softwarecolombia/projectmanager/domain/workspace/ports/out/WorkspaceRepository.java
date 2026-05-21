package com.softwarecolombia.projectmanager.domain.workspace.ports.out;

import com.softwarecolombia.projectmanager.domain.workspace.model.WorkspaceWithUserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WorkspaceRepository {
    Flux<WorkspaceWithUserRole> findWorkspacesWithUserRoleByUserId(Long userId);

    Mono<String> findWorkspaceUserRoleByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    Mono<Boolean> appUserIsInWorkspace(Long userId, Long workspaceId);
}
