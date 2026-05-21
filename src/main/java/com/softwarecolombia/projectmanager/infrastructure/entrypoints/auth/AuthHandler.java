package com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth;

import com.softwarecolombia.projectmanager.domain.user.ports.in.LoginAppUserUseCase;
import com.softwarecolombia.projectmanager.domain.user.ports.in.LoginWorkspaceContextUseCase;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.dto.request.LoginRequestDTO;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.mapper.LoginAppUserMapper;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.config.ApiResponse;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.config.validators.ServerRequestValidator;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.exceptions.ServerRequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@AllArgsConstructor
public class AuthHandler {
    private final ServerRequestValidator serverRequestValidator;
    private final LoginAppUserUseCase loginUserUseCase;
    private final LoginWorkspaceContextUseCase loginWorkspaceContextUseCase;
    private final LoginAppUserMapper loginAppUserMapper;

    public Mono<ServerResponse> listenPOSTLogin(ServerRequest serverRequest){
        return serverRequest.bodyToMono(LoginRequestDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("User can't be empty")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(loginRequestDTO -> loginUserUseCase.loginAppUser(loginRequestDTO.email(), loginRequestDTO.password()))
                .map(loginAppUserMapper::toResponseDTO)
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"User logged successfully"))
                );
    }

    public Mono<ServerResponse> listenPOSTToken(ServerRequest serverRequest){
        return Mono.justOrEmpty(serverRequest.queryParam("workspaceRequest_Id"))
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("No data recievied")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(workspaceRequestId -> loginWorkspaceContextUseCase.authWorkspace(Long.valueOf(workspaceRequestId)))
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"User with workspace logged successfully"))
                );
    }
}
