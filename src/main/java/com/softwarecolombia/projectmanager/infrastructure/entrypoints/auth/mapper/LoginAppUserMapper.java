package com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.mapper;

import com.softwarecolombia.projectmanager.domain.user.model.AppUserWithWorkspaceAndRole;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.dto.response.LoginAppUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginAppUserMapper {
    @Mapping(source = "appUser", target = "appUserResponseDTO")
    @Mapping(source = "workspacesWithRole", target = "workspaceWithRoleDTO")
    LoginAppUserResponseDTO toResponseDTO(AppUserWithWorkspaceAndRole appUserWithWorkspaceAndRole);
}

