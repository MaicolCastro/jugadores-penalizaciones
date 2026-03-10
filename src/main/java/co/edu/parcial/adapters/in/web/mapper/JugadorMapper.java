package co.edu.parcial.adapters.in.web.mapper;

import co.edu.parcial.adapters.in.web.dto.JugadorDtos;
import co.edu.parcial.domain.model.Jugador;

public final class JugadorMapper {
    private JugadorMapper() {}

    public static JugadorDtos.Response toResponse(Jugador j) {
        return new JugadorDtos.Response(j.id(), j.nombre(), j.email(), j.edad(), j.createdAt());
    }
}

