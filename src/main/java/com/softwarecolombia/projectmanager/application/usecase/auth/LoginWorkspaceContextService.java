package com.softwarecolombia.projectmanager.application.usecase.auth;

import com.softwarecolombia.projectmanager.domain.security.ports.out.JwtProvider;
import com.softwarecolombia.projectmanager.domain.shared.exceptions.BusinessException;
import com.softwarecolombia.projectmanager.domain.user.ports.in.LoginWorkspaceContextUseCase;
import com.softwarecolombia.projectmanager.domain.user.ports.out.AppUserRepository;
import com.softwarecolombia.projectmanager.domain.workspace.ports.out.WorkspaceRepository;
import com.softwarecolombia.projectmanager.infrastructure.config.security.AuthPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LoginWorkspaceContextService implements LoginWorkspaceContextUseCase {
    private final AppUserRepository appUserRepository;
    private final WorkspaceRepository workspaceRepository;
    private final JwtProvider jwtProvider;

    @Override
    public Mono<String> authWorkspace(Long workspaceId) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (AuthPrincipal) securityContext.getAuthentication().getPrincipal())
                .flatMap(authPrincipal -> {
                    Long userId = authPrincipal.userId();
                    return appUserRepository.existsById(userId)
                            .flatMap(userExists -> {
                                if (!userExists)
                                    return Mono.error(new BusinessException("User not found"));

                                return workspaceRepository.findWorkspaceUserRoleByUserIdAndWorkspaceId(userId, workspaceId)
                                        .switchIfEmpty(Mono.error(new BusinessException("Workspace not found or user not a member")))
                                        .map(workspaceRole -> jwtProvider.generateAuthToken(userId, workspaceId, workspaceRole));
                            });
                });
    }
}