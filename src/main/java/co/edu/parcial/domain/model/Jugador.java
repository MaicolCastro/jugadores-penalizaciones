package co.edu.parcial.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Jugador(
        UUID id,
        String nombre,
        String email,
        Integer edad,
        Instant createdAt
) {
}

