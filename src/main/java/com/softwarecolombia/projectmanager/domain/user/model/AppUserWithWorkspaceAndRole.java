package com.softwarecolombia.projectmanager.domain.user.model;

import com.softwarecolombia.projectmanager.domain.workspace.model.WorkspaceWithUserRole;

import java.util.List;

public record AppUserWithWorkspaceAndRole(AppUser appUser, List<WorkspaceWithUserRole> workspacesWithRole, String preAuthToken) {
}
