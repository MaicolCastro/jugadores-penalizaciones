package co.edu.parcial.config;

import co.edu.parcial.application.service.AuthService;
import co.edu.parcial.application.service.JugadorService;
import co.edu.parcial.application.service.TarjetaPenalizacionService;
import co.edu.parcial.domain.ports.in.AuthUseCase;
import co.edu.parcial.domain.ports.in.JugadorUseCase;
import co.edu.parcial.domain.ports.in.TarjetaPenalizacionUseCase;
import co.edu.parcial.domain.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public AuthUseCase authUseCase(UserRepositoryPort users, PasswordHasherPort hasher, JwtPort jwt) {
        return new AuthService(users, hasher, jwt);
    }

    @Bean
    public JugadorUseCase jugadorUseCase(JugadorRepositoryPort jugadores) {
        return new JugadorService(jugadores);
    }

    @Bean
    public TarjetaPenalizacionUseCase tarjetaUseCase(TarjetaRepositoryPort tarjetas, JugadorRepositoryPort jugadores) {
        return new TarjetaPenalizacionService(tarjetas, jugadores);
    }
}

