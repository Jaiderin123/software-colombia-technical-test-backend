package com.softwarecolombia.projectmanager.domain.workspace.ports.out;

import com.softwarecolombia.projectmanager.domain.workspace.model.WorkspaceWithUserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IWorkspaceRepository {
    Flux<WorkspaceWithUserRole> findWorkspacesWithUserRoleByUserId(Long userId);

    Mono<String> findWorkspaceUserRoleByUserIdAndWorkspaceId(Long userId, Long workspaceId);
}
