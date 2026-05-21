package com.softwarecolombia.projectmanager.infrastructure.entrypoints.project;

import com.softwarecolombia.projectmanager.infrastructure.config.AppUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class ProjectRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> projectRoutes(ProjectHandler projectHandler, AppUrlConfig appUrlConfig){
        //log.info("Franchise base Path APP -> {}", basePath);
        return nest(
                path(appUrlConfig.getBaseUrl() + "/project"),
                route(
                        GET("/"),
                        projectHandler::listenGETProjects
                ).andRoute(
                        POST("/"),
                        projectHandler::listenPOSTCreateProject
                )
        );
    }
}
