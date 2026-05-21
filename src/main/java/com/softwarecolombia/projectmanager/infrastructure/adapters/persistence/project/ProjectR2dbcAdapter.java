package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project;

import com.softwarecolombia.projectmanager.domain.project.model.Project;
import com.softwarecolombia.projectmanager.domain.project.ports.out.ProjectRepository;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.mapper.ProjectMapper;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.r2dbc.ProjectR2dbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class ProjectR2dbcAdapter implements ProjectRepository {
    private final ProjectR2dbcRepository projectR2dbcRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Flux<Project> getProjectsByWorkspaceId(Long workspaceId) {
        return projectR2dbcRepository.findByWorkspaceId(workspaceId)
                .map(projectMapper::toDomain);
    }

    @Override
    public Mono<Void> createProject(Project project) {
        return projectR2dbcRepository.save(projectMapper.toEntity(project)).then();
    }
}
