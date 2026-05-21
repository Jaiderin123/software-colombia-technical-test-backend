package com.softwarecolombia.projectmanager.domain.project.ports.out;

import com.softwarecolombia.projectmanager.domain.project.model.Project;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectRepository {
    Flux<Project> getProjectsByWorkspaceId(Long workspaceId);

    Mono<Void> createProject(Project project);
}
