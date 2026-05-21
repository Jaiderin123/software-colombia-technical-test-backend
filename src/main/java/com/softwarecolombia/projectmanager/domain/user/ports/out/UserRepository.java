package com.softwarecolombia.projectmanager.domain.user.ports.out;

import com.softwarecolombia.projectmanager.domain.user.model.AppUser;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository {
    Mono<AppUser> findByEmail(String email);

    Mono<Boolean> existsById(Long userId);
}
