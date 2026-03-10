package co.edu.parcial.domain.model;

import java.time.Instant;
import java.util.UUID;

public record TarjetaPenalizacion(
        UUID id,
        UUID jugadorId,
        TipoPenalizacion tipo,
        Integer puntos,
        String descripcion,
        Instant fecha
) {
}

