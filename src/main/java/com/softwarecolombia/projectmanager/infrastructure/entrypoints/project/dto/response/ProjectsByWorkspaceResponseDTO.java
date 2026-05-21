package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.dto.response;

import java.util.List;

public record ProjectsByWorkspaceResponseDTO(
        Long workspaceId,
        List<ProjectResponseDTO> projectList
) {
}
