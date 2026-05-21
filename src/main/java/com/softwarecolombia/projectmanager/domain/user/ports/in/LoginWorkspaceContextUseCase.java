package com.softwarecolombia.projectmanager.domain.user.ports.in;

import reactor.core.publisher.Mono;

public interface LoginWorkspaceContextUseCase {
    Mono<String> authWorkspace(Long workspaceId);
}
