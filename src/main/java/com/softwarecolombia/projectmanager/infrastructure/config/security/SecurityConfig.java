package com.softwarecolombia.projectmanager.infrastructure.config.security;

import com.softwarecolombia.projectmanager.infrastructure.config.AppUrlConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler, AppUrlConfig appUrlConfig) {
        String baseUrl = appUrlConfig.getBaseUrl();

        return http
                // disable web app classic protection
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)

                // Routes
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(POST, baseUrl + "/auth/login").permitAll()
                        //with pre-auth token
                        .pathMatchers(POST, baseUrl + "/auth/token").authenticated()

                        .pathMatchers(GET, baseUrl + "/projects/**").authenticated()
                        .pathMatchers(POST, baseUrl + "/projects/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")

                        //show errors
                        .pathMatchers("/error").permitAll()
                        // other routes has to be authenticated
                        .anyExchange().authenticated()
                )

                // Filters
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                // Exceptions
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
