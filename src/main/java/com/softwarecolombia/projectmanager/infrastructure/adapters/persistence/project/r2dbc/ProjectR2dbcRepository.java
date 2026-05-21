package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.r2dbc;

import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.entity.ProjectEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProjectR2dbcRepository extends R2dbcRepository<ProjectEntity, Long> {
    Flux<ProjectEntity> findByWorkspaceId(Long workspaceId);
}
