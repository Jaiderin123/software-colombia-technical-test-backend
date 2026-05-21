package com.softwarecolombia.projectmanager.application.usecase.project;

import com.softwarecolombia.projectmanager.domain.project.model.Project;
import com.softwarecolombia.projectmanager.domain.project.ports.in.CreateProjectUseCase;
import com.softwarecolombia.projectmanager.domain.project.ports.out.ProjectRepository;
import com.softwarecolombia.projectmanager.domain.shared.exceptions.BusinessException;
import com.softwarecolombia.projectmanager.domain.user.ports.out.AppUserRepository;
import com.softwarecolombia.projectmanager.domain.workspace.ports.out.WorkspaceRepository;
import com.softwarecolombia.projectmanager.infrastructure.config.security.AuthPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public Mono<Void> createProject(String name, String description) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (AuthPrincipal) securityContext.getAuthentication().getPrincipal())
                .flatMap(authPrincipal -> {
                    Long workspaceId = authPrincipal.workspaceId();
                    return appUserRepository.findById(authPrincipal.userId())
                            .switchIfEmpty(Mono.error(new BusinessException("User not found")))
                            .flatMap(appUser ->
                                    workspaceRepository.appUserIsInWorkspace(appUser.id(), workspaceId)
                                            .filter(appUserIsInWorkspace -> appUserIsInWorkspace)
                                            .switchIfEmpty(Mono.error(new BusinessException("User doesn't have permission to create project in this workspace")))
                                            .flatMap(appUserIsInWorkspace ->
                                                    projectRepository.createProject(new Project(workspaceId, name, description))
                                            )
                            );
                });
    }
}
