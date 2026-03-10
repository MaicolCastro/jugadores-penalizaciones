package co.edu.parcial.adapters.out.persistence.repository;

import co.edu.parcial.adapters.out.persistence.entity.TarjetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TarjetaR2dbcRepository extends ReactiveCrudRepository<TarjetaEntity, String> {
    Flux<TarjetaEntity> findAll();
    Flux<TarjetaEntity> findAllByJugadorId(String jugadorId);
}

