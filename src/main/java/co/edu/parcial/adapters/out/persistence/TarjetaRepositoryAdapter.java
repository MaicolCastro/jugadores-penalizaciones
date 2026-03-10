package co.edu.parcial.adapters.out.persistence;

import co.edu.parcial.adapters.out.persistence.mapper.PersistenceMappers;
import co.edu.parcial.adapters.out.persistence.repository.TarjetaR2dbcRepository;
import co.edu.parcial.domain.model.TarjetaPenalizacion;
import co.edu.parcial.domain.ports.out.TarjetaRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TarjetaRepositoryAdapter implements TarjetaRepositoryPort {
    private final TarjetaR2dbcRepository repo;

    public TarjetaRepositoryAdapter(TarjetaR2dbcRepository repo) {
        this.repo = repo;
    }

    @Override
    public Mono<TarjetaPenalizacion> save(TarjetaPenalizacion tarjeta) {
        var entity = PersistenceMappers.toEntity(tarjeta);
        return repo.existsById(entity.getId())
                .map(exists -> {
                    entity.setNew(!exists);
                    return entity;
                })
                .flatMap(repo::save)
                .map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<TarjetaPenalizacion> findById(UUID id) {
        return repo.findById(id.toString()).map(PersistenceMappers::toDomain);
    }

    @Override
    public Flux<TarjetaPenalizacion> findAll() {
        return repo.findAll().map(PersistenceMappers::toDomain);
    }

    @Override
    public Flux<TarjetaPenalizacion> findAllByJugadorId(UUID jugadorId) {
        return repo.findAllByJugadorId(jugadorId.toString()).map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return repo.deleteById(id.toString());
    }
}

