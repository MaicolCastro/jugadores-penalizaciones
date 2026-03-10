package co.edu.parcial.domain.ports.out;

import co.edu.parcial.domain.model.TarjetaPenalizacion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TarjetaRepositoryPort {
    Mono<TarjetaPenalizacion> save(TarjetaPenalizacion tarjeta);
    Mono<TarjetaPenalizacion> findById(UUID id);
    Flux<TarjetaPenalizacion> findAll();
    Flux<TarjetaPenalizacion> findAllByJugadorId(UUID jugadorId);
    Mono<Void> deleteById(UUID id);
}

