package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.mapper;

import com.softwarecolombia.projectmanager.domain.project.model.ProjectsByWorkspace;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.dto.response.ProjectsByWorkspaceResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectsByWorkspaceMapper {
    ProjectsByWorkspaceResponseDTO toResponseDTO(ProjectsByWorkspace projectsByWorkspace);
}
