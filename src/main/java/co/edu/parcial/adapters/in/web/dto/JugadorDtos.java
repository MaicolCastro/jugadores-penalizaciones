package co.edu.parcial.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class JugadorDtos {
    private JugadorDtos() {}

    public record CreateRequest(
            @NotBlank @Size(min = 2, max = 80) String nombre,
            @NotBlank @Email @Size(max = 120) String email,
            @NotNull @Min(5) @Max(99) Integer edad
    ) {}

    public record UpdateRequest(
            @NotBlank @Size(min = 2, max = 80) String nombre,
            @NotBlank @Email @Size(max = 120) String email,
            @NotNull @Min(5) @Max(99) Integer edad
    ) {}

    public record Response(UUID id, String nombre, String email, Integer edad, Instant createdAt) {}
}

