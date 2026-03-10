package co.edu.parcial.domain.model;

import java.time.Instant;
import java.util.UUID;

public record AppUser(
        UUID id,
        String username,
        String passwordHash,
        Role role,
        Instant createdAt
) {
}

