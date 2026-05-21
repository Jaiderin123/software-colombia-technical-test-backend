package com.softwarecolombia.projectmanager.domain.shared.exceptions.ports;

import reactor.core.publisher.Mono;

public interface IPasswordEncoderPort {
    Mono<String> encode(String rawPassword);
    Mono<Boolean> matches(String rawPassword, String encodedPassword);
}
