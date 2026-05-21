package com.softwarecolombia.projectmanager.domain.security.ports.out;

public interface JwtProvider {
    String generatePreAuthToken(Long userId);

    String generateAuthToken(Long userId, Long workspaceId, String workspaceRole);
}
