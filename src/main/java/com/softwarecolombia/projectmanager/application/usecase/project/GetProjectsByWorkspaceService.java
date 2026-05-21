package com.softwarecolombia.projectmanager.application.usecase.project;

import com.softwarecolombia.projectmanager.domain.project.model.ProjectsByWorkspace;
import com.softwarecolombia.projectmanager.domain.project.ports.in.GetProjectsByWorkspaceUseCase;
import com.softwarecolombia.projectmanager.domain.project.ports.out.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetProjectsByWorkspaceService implements GetProjectsByWorkspaceUseCase {
    private final ProjectRepository projectRepository;

    @Override
    public Mono<ProjectsByWorkspace> getProjectsByWorkspace(Long workspaceId) {
        return projectRepository.getProjectsByWorkspaceId(workspaceId)
                .collectList()
                .map(projectList -> new ProjectsByWorkspace(workspaceId, projectList));
    }
}
