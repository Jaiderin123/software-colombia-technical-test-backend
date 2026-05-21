package com.softwarecolombia.projectmanager.domain.project.ports.in;

import com.softwarecolombia.projectmanager.domain.project.model.ProjectsByWorkspace;
import reactor.core.publisher.Mono;

public interface GetProjectsByWorkspaceUseCase {
    Mono<ProjectsByWorkspace> getProjectsByWorkspace(Long workspaceId);
}
