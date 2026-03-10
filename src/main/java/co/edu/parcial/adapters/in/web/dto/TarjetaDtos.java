package co.edu.parcial.adapters.in.web.dto;

import co.edu.parcial.domain.model.TipoPenalizacion;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class TarjetaDtos {
    private TarjetaDtos() {}

    public record CreateRequest(
            @NotNull UUID jugadorId,
            @NotNull TipoPenalizacion tipo,
            @NotNull @Min(0) @Max(100) Integer puntos,
            @NotBlank @Size(min = 3, max = 200) String descripcion
    ) {}

    public record UpdateRequest(
            @NotNull TipoPenalizacion tipo,
            @NotNull @Min(0) @Max(100) Integer puntos,
            @NotBlank @Size(min = 3, max = 200) String descripcion
    ) {}

    public record Response(
            UUID id,
            UUID jugadorId,
            TipoPenalizacion tipo,
            Integer puntos,
            String descripcion,
            Instant fecha
    ) {}
}

