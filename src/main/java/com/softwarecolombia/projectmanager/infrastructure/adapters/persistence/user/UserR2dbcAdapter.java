package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.user;

import com.softwarecolombia.projectmanager.domain.user.model.AppUser;
import com.softwarecolombia.projectmanager.domain.user.ports.out.UserRepository;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.user.mapper.UserMapper;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.user.r2dbc.UserR2dbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class UserR2dbcAdapter implements UserRepository {
    private final UserR2dbcRepository userR2dbcRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<AppUser> findByEmail(String email) {
        return userR2dbcRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(Long userId) {
        return userR2dbcRepository.existsById(userId);
    }
}
