package co.edu.parcial.adapters.in.web;

import co.edu.parcial.adapters.in.web.dto.JugadorDtos;
import co.edu.parcial.adapters.in.web.mapper.JugadorMapper;
import co.edu.parcial.domain.ports.in.JugadorUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {
    private final JugadorUseCase jugadores;

    public JugadorController(JugadorUseCase jugadores) {
        this.jugadores = jugadores;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<JugadorDtos.Response> crear(@Valid @RequestBody JugadorDtos.CreateRequest req) {
        return jugadores.crear(new JugadorUseCase.CrearJugadorCommand(req.nombre(), req.email(), req.edad()))
                .map(JugadorMapper::toResponse);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Flux<JugadorDtos.Response> listar() {
        return jugadores.listar().map(JugadorMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Mono<JugadorDtos.Response> obtener(@PathVariable UUID id) {
        return jugadores.obtener(id).map(JugadorMapper::toResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<JugadorDtos.Response> actualizar(@PathVariable UUID id, @Valid @RequestBody JugadorDtos.UpdateRequest req) {
        return jugadores.actualizar(id, new JugadorUseCase.ActualizarJugadorCommand(req.nombre(), req.email(), req.edad()))
                .map(JugadorMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return jugadores.eliminar(id);
    }
}

