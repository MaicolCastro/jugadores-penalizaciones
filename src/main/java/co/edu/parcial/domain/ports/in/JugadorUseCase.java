package co.edu.parcial.domain.ports.in;

import co.edu.parcial.domain.model.Jugador;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JugadorUseCase {
    Mono<Jugador> crear(CrearJugadorCommand command);
    Mono<Jugador> actualizar(UUID id, ActualizarJugadorCommand command);
    Mono<Void> eliminar(UUID id);
    Mono<Jugador> obtener(UUID id);
    Flux<Jugador> listar();

    record CrearJugadorCommand(String nombre, String email, Integer edad) {}
    record ActualizarJugadorCommand(String nombre, String email, Integer edad) {}
}

