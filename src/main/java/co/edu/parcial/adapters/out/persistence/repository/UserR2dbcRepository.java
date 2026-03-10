package co.edu.parcial.adapters.out.persistence.repository;

import co.edu.parcial.adapters.out.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserR2dbcRepository extends ReactiveCrudRepository<UserEntity, String> {
    Mono<UserEntity> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
}

