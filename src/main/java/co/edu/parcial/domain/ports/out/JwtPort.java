package co.edu.parcial.domain.ports.out;

import co.edu.parcial.domain.model.AppUser;

public interface JwtPort {
    String generateAccessToken(AppUser user);
}

