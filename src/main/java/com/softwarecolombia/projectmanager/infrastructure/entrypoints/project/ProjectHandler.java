package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project;

import com.softwarecolombia.projectmanager.infrastructure.entrypoints.auth.dto.request.LoginRequestDTO;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.config.ApiResponse;
import com.softwarecolombia.projectmanager.infrastructure.entrypoints.exceptions.ServerRequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@AllArgsConstructor
public class ProjectHandler {

    public Mono<ServerResponse> listenPOSTLogin(ServerRequest serverRequest){
        return serverRequest.bodyToMono(LoginRequestDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Franchise can't be empty")))
                //.flatMap(serverRequestValidator::validate)
                //.map(CreateFranchiseDTO::name)
                //.flatMap(createFranchiseUseCase::createFranchise)
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"Franchise created successfully"))
                );
    }
}
