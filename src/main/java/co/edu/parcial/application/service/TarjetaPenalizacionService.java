package co.edu.parcial.application.service;

import co.edu.parcial.domain.exception.NotFoundException;
import co.edu.parcial.domain.model.TarjetaPenalizacion;
import co.edu.parcial.domain.ports.in.TarjetaPenalizacionUseCase;
import co.edu.parcial.domain.ports.out.JugadorRepositoryPort;
import co.edu.parcial.domain.ports.out.TarjetaRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public class TarjetaPenalizacionService implements TarjetaPenalizacionUseCase {
    private final TarjetaRepositoryPort tarjetas;
    private final JugadorRepositoryPort jugadores;

    public TarjetaPenalizacionService(TarjetaRepositoryPort tarjetas, JugadorRepositoryPort jugadores) {
        this.tarjetas = tarjetas;
        this.jugadores = jugadores;
    }

    @Override
    public Mono<TarjetaPenalizacion> crear(CrearTarjetaCommand command) {
        return jugadores.findById(command.jugadorId())
                .switchIfEmpty(Mono.error(new NotFoundException("Jugador no encontrado")))
                .flatMap(j -> tarjetas.save(new TarjetaPenalizacion(
                        UUID.randomUUID(),
                        command.jugadorId(),
                        command.tipo(),
                        command.puntos(),
                        command.descripcion(),
                        Instant.now()
                )));
    }

    @Override
    public Mono<TarjetaPenalizacion> actualizar(UUID id, ActualizarTarjetaCommand command) {
        return tarjetas.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Tarjeta no encontrada")))
                .flatMap(existing -> tarjetas.save(new TarjetaPenalizacion(
                        existing.id(),
                        existing.jugadorId(),
                        command.tipo(),
                        command.puntos(),
                        command.descripcion(),
                        existing.fecha()
                )));
    }

    @Override
    public Mono<Void> eliminar(UUID id) {
        return tarjetas.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Tarjeta no encontrada")))
                .flatMap(t -> tarjetas.deleteById(id));
    }

    @Override
    public Mono<TarjetaPenalizacion> obtener(UUID id) {
        return tarjetas.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Tarjeta no encontrada")));
    }

    @Override
    public Flux<TarjetaPenalizacion> listar() {
        return tarjetas.findAll();
    }

    @Override
    public Flux<TarjetaPenalizacion> listarPorJugador(UUID jugadorId) {
        return tarjetas.findAllByJugadorId(jugadorId);
    }
}

