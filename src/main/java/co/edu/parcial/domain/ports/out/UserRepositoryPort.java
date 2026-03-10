package co.edu.parcial.domain.ports.out;

import co.edu.parcial.domain.model.AppUser;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepositoryPort {
    Mono<AppUser> save(AppUser user);
    Mono<AppUser> findById(UUID id);
    Mono<AppUser> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
}

