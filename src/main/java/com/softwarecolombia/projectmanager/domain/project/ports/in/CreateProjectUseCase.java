package com.softwarecolombia.projectmanager.domain.project.ports.in;

import reactor.core.publisher.Mono;

public interface CreateProjectUseCase {
    Mono<Void> createProject(String name, String description);
}
