package co.edu.parcial.adapters.in.web;

import co.edu.parcial.adapters.in.web.dto.TarjetaDtos;
import co.edu.parcial.adapters.in.web.mapper.TarjetaMapper;
import co.edu.parcial.domain.ports.in.TarjetaPenalizacionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaPenalizacionController {
    private final TarjetaPenalizacionUseCase tarjetas;

    public TarjetaPenalizacionController(TarjetaPenalizacionUseCase tarjetas) {
        this.tarjetas = tarjetas;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<TarjetaDtos.Response> crear(@Valid @RequestBody TarjetaDtos.CreateRequest req) {
        return tarjetas.crear(new TarjetaPenalizacionUseCase.CrearTarjetaCommand(
                        req.jugadorId(),
                        req.tipo(),
                        req.puntos(),
                        req.descripcion()
                ))
                .map(TarjetaMapper::toResponse);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Flux<TarjetaDtos.Response> listar(@RequestParam(name = "jugadorId", required = false) UUID jugadorId) {
        return (jugadorId == null ? tarjetas.listar() : tarjetas.listarPorJugador(jugadorId))
                .map(TarjetaMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Mono<TarjetaDtos.Response> obtener(@PathVariable UUID id) {
        return tarjetas.obtener(id).map(TarjetaMapper::toResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<TarjetaDtos.Response> actualizar(@PathVariable UUID id, @Valid @RequestBody TarjetaDtos.UpdateRequest req) {
        return tarjetas.actualizar(id, new TarjetaPenalizacionUseCase.ActualizarTarjetaCommand(req.tipo(), req.puntos(), req.descripcion()))
                .map(TarjetaMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return tarjetas.eliminar(id);
    }
}

