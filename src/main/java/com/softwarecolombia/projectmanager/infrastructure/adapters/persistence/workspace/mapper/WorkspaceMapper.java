package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.mapper;

import com.softwarecolombia.projectmanager.domain.workspace.model.WorkspaceWithUserRole;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.entity.WorkspaceWithUserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    WorkspaceWithUserRole toDomain(WorkspaceWithUserRoleEntity workspaceWithUserRoleEntity);
}
