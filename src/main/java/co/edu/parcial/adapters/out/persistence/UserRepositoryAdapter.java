package co.edu.parcial.adapters.out.persistence;

import co.edu.parcial.adapters.out.persistence.mapper.PersistenceMappers;
import co.edu.parcial.adapters.out.persistence.repository.UserR2dbcRepository;
import co.edu.parcial.domain.model.AppUser;
import co.edu.parcial.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserR2dbcRepository repo;

    public UserRepositoryAdapter(UserR2dbcRepository repo) {
        this.repo = repo;
    }

    @Override
    public Mono<AppUser> save(AppUser user) {
        var entity = PersistenceMappers.toEntity(user);
        return repo.existsById(entity.getId())
                .map(exists -> {
                    entity.setNew(!exists);
                    return entity;
                })
                .flatMap(repo::save)
                .map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<AppUser> findById(UUID id) {
        return repo.findById(id.toString()).map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<AppUser> findByUsername(String username) {
        return repo.findByUsername(username).map(PersistenceMappers::toDomain);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return repo.existsByUsername(username);
    }
}

