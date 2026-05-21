package com.softwarecolombia.projectmanager.domain.project.model;

import java.util.List;

public record ProjectsByWorkspace(
        Long workspaceId,
        List<Project> projectList
) {
}
