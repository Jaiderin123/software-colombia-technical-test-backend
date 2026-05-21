package com.softwarecolombia.projectmanager.infrastructure.config.security;

public record AuthPrincipal(Long userId, Long workspaceId, String role) {
}
