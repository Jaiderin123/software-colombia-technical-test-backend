package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace;

import com.softwarecolombia.projectmanager.domain.workspace.model.WorkspaceWithUserRole;
import com.softwarecolombia.projectmanager.domain.workspace.ports.out.WorkspaceRepository;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.mapper.WorkspaceMapper;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.r2dbc.WorkspaceR2dbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class WorkspaceAdapter implements WorkspaceRepository {
    private final WorkspaceR2dbcRepository workspaceR2dbcRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    public Flux<WorkspaceWithUserRole> findWorkspacesWithUserRoleByUserId(Long userId) {
        return workspaceR2dbcRepository.findWorkspacesWithRoleByUserId(userId)
                .map(workspaceMapper::toDomain);
    }

    @Override
    public Mono<String> findWorkspaceUserRoleByUserIdAndWorkspaceId(Long userId, Long workspaceId) {
        return workspaceR2dbcRepository.findWorkspaceUserRoleByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @Override
    public Mono<Boolean> appUserIsInWorkspace(Long userId, Long workspaceId) {
        return workspaceR2dbcRepository.appUserIsInWorkspace(userId, workspaceId)
                .map(count -> count > 0);
    }
}
