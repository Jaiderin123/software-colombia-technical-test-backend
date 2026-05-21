package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project;

import com.softwarecolombia.projectmanager.domain.project.ports.in.CreateProjectUseCase;
import com.softwarecolombia.projectmanager.domain.project.ports.in.GetProjectsByWorkspaceUseCase;
import com.softwarecolombia.projectmanager.infrastructure.config.security.AuthPrincipal;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.dto.request.LoginRequestDTO;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.config.ApiResponse;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.exceptions.ServerRequestValidationException;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.dto.request.CreateProjectRequestDTO;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.project.mapper.ProjectsByWorkspaceMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@AllArgsConstructor
public class ProjectHandler {
    private final GetProjectsByWorkspaceUseCase getProjectsByWorkspaceUseCase;
    private final ProjectsByWorkspaceMapper projectsByWorkspaceMapper;
    private final CreateProjectUseCase createProjectUseCase;

    public Mono<ServerResponse> listenGETProjects(ServerRequest serverRequest){
        return serverRequest.principal()
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("User not logged in")))
                .cast(UsernamePasswordAuthenticationToken.class)
                .map(UsernamePasswordAuthenticationToken::getPrincipal)
                .cast(AuthPrincipal.class)
                .map(AuthPrincipal::workspaceId)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("User don't have permission to access the workspace")))
                .flatMap(getProjectsByWorkspaceUseCase::getProjectsByWorkspace)
                .map(projectsByWorkspaceMapper::toResponseDTO)
                .flatMap(responseData ->
                        ServerResponse.status(HttpStatus.OK)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,200,"Projects retrieved"))
                );
    }

    public Mono<ServerResponse> listenPOSTCreateProject(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateProjectRequestDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Project info is required")))
                .flatMap(createProjectRequestDTO -> createProjectUseCase.createProject(createProjectRequestDTO.name(), createProjectRequestDTO.description()))
                .then(
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(201,"Project created successfully"))
                );
    }
}
