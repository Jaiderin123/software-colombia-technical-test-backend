package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.mapper;

import com.softwarecolombia.projectmanager.domain.project.model.Project;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toDomain(ProjectEntity project);

    ProjectEntity toEntity(Project project);
}
