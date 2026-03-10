package co.edu.parcial.domain.ports.in;

import co.edu.parcial.domain.model.TarjetaPenalizacion;
import co.edu.parcial.domain.model.TipoPenalizacion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TarjetaPenalizacionUseCase {
    Mono<TarjetaPenalizacion> crear(CrearTarjetaCommand command);
    Mono<TarjetaPenalizacion> actualizar(UUID id, ActualizarTarjetaCommand command);
    Mono<Void> eliminar(UUID id);
    Mono<TarjetaPenalizacion> obtener(UUID id);
    Flux<TarjetaPenalizacion> listar();
    Flux<TarjetaPenalizacion> listarPorJugador(UUID jugadorId);

    record CrearTarjetaCommand(UUID jugadorId, TipoPenalizacion tipo, Integer puntos, String descripcion) {}
    record ActualizarTarjetaCommand(TipoPenalizacion tipo, Integer puntos, String descripcion) {}
}

