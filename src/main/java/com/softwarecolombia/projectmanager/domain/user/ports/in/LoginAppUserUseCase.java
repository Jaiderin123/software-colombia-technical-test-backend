package com.softwarecolombia.projectmanager.domain.user.ports.in;

import com.softwarecolombia.projectmanager.domain.user.model.AppUserWithWorkspaceAndRole;
import reactor.core.publisher.Mono;

public interface LoginAppUserUseCase {
    Mono<AppUserWithWorkspaceAndRole> loginAppUser(String email, String password);
}
