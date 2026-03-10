package co.edu.parcial.adapters.in.web.mapper;

import co.edu.parcial.adapters.in.web.dto.TarjetaDtos;
import co.edu.parcial.domain.model.TarjetaPenalizacion;

public final class TarjetaMapper {
    private TarjetaMapper() {}

    public static TarjetaDtos.Response toResponse(TarjetaPenalizacion t) {
        return new TarjetaDtos.Response(t.id(), t.jugadorId(), t.tipo(), t.puntos(), t.descripcion(), t.fecha());
    }
}

