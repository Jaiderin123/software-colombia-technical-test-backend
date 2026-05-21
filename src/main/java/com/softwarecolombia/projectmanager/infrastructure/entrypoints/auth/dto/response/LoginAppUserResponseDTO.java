package com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.dto.response;

import java.util.List;

public record LoginAppUserResponseDTO(AppUserResponseDTO appUserResponseDTO, List<WorkspaceWithRoleDTO> workspaceWithRoleDTO, String preAuthToken) {
}
