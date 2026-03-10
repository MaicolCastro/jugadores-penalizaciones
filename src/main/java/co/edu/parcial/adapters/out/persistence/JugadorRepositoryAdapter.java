package co.edu.parcial.adapters.out.persistence;

import co.edu.parcial.adapters.out.persistence.mapper.PersistenceMappers;
import co.edu.parcial.adapters.out.persistence.repository.JugadorR2dbcRepository;
import co.edu.parcial.domain.model.Jugador;
import co.edu.parcial.domain.ports.out.JugadorRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class JugadorRepositoryAdapter implements JugadorRepositoryPort {
    private final JugadorR2dbcRepository repo;

    public JugadorRepositoryAdapter(JugadorR2dbcRepository repo) {
        this.repo = repo;
    }

    @Override
    public Mono<Jugador> save(Jugador jugador) {
        var entity = PersistenceMappers.toEntity(jugador);
        return repo.existsById(entity.getId())
                .map(exists -> {
                    entity.setNew(!exists);
                    return entity;
                })
                .flatMap(repo::save)
                .map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<Jugador> findById(UUID id) {
        return repo.findById(id.toString()).map(PersistenceMappers::toDomain);
    }

    @Override
    public Flux<Jugador> findAll() {
        return repo.findAll().map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return repo.deleteById(id.toString());
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByEmailAndIdNot(String email, UUID id) {
        return repo.existsByEmailAndIdNot(email, id.toString());
    }
}

