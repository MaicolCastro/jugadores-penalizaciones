package co.edu.parcial.adapters.out.persistence.repository;

import co.edu.parcial.adapters.out.persistence.entity.JugadorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JugadorR2dbcRepository extends ReactiveCrudRepository<JugadorEntity, String> {
    Flux<JugadorEntity> findAll();
    Mono<Boolean> existsByEmail(String email);

    @Query("SELECT EXISTS(SELECT 1 FROM jugadores WHERE email = :email AND id <> :id)")
    Mono<Boolean> existsByEmailAndIdNot(String email, String id);
}

