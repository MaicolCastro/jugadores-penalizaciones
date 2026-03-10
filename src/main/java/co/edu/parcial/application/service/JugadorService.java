package co.edu.parcial.application.service;

import co.edu.parcial.domain.exception.ConflictException;
import co.edu.parcial.domain.exception.NotFoundException;
import co.edu.parcial.domain.model.Jugador;
import co.edu.parcial.domain.ports.in.JugadorUseCase;
import co.edu.parcial.domain.ports.out.JugadorRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public class JugadorService implements JugadorUseCase {
    private final JugadorRepositoryPort repo;

    public JugadorService(JugadorRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    public Mono<Jugador> crear(CrearJugadorCommand command) {
        return repo.existsByEmail(command.email().trim().toLowerCase())
                .flatMap(exists -> exists
                        ? Mono.<Jugador>error(new ConflictException("El email ya está registrado"))
                        : repo.save(new Jugador(
                                UUID.randomUUID(),
                                command.nombre().trim(),
                                command.email().trim().toLowerCase(),
                                command.edad(),
                                Instant.now()
                        )));
    }

    @Override
    public Mono<Jugador> actualizar(UUID id, ActualizarJugadorCommand command) {
        String email = command.email().trim().toLowerCase();
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Jugador no encontrado")))
                .flatMap(existing ->
                        repo.existsByEmailAndIdNot(email, id)
                                .flatMap(conflict -> conflict
                                        ? Mono.<Jugador>error(new ConflictException("El email ya está registrado"))
                                        : repo.save(new Jugador(
                                                existing.id(),
                                                command.nombre().trim(),
                                                email,
                                                command.edad(),
                                                existing.createdAt()
                                        ))));
    }

    @Override
    public Mono<Void> eliminar(UUID id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Jugador no encontrado")))
                .flatMap(j -> repo.deleteById(id));
    }

    @Override
    public Mono<Jugador> obtener(UUID id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Jugador no encontrado")));
    }

    @Override
    public Flux<Jugador> listar() {
        return repo.findAll();
    }
}

