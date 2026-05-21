package com.softwarecolombia.projectmanager.application.usecase.auth;

import com.softwarecolombia.projectmanager.domain.security.ports.out.JwtProvider;
import com.softwarecolombia.projectmanager.domain.shared.exceptions.BusinessException;
import com.softwarecolombia.projectmanager.domain.user.model.AppUserWithWorkspaceAndRole;
import com.softwarecolombia.projectmanager.domain.shared.exceptions.ports.IPasswordEncoderPort;
import com.softwarecolombia.projectmanager.domain.user.ports.in.LoginAppUserUseCase;
import com.softwarecolombia.projectmanager.domain.user.ports.out.AppUserRepository;
import com.softwarecolombia.projectmanager.domain.workspace.ports.out.WorkspaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LoginAppUserService implements LoginAppUserUseCase {
    private final AppUserRepository appUserRepository;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final WorkspaceRepository workspaceRepository;
    private final JwtProvider jwtProvider;

    @Override
    public Mono<AppUserWithWorkspaceAndRole> loginAppUser(String email, String password) {
        return appUserRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException("User don't exits")))
                .flatMap(userApp ->
                    passwordEncoderPort.matches(password, userApp.password())
                            .flatMap(passwordMatches -> {
                                if (!passwordMatches)
                                    return Mono.error(new BusinessException("Invalid credentials"));

                                return workspaceRepository.findWorkspacesWithUserRoleByUserId(userApp.id())
                                        .collectList()
                                        .map(workspaces -> {
                                            String preAuthToken = jwtProvider.generatePreAuthToken(userApp.id());
                                            return new AppUserWithWorkspaceAndRole(userApp, workspaces, preAuthToken);
                                        });
                            })
                );
    }
}
