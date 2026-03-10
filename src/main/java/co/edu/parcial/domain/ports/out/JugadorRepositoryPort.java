package co.edu.parcial.domain.ports.out;

import co.edu.parcial.domain.model.Jugador;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JugadorRepositoryPort {
    Mono<Jugador> save(Jugador jugador);
    Mono<Jugador> findById(UUID id);
    Flux<Jugador> findAll();
    Mono<Void> deleteById(UUID id);
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByEmailAndIdNot(String email, UUID id);
}

