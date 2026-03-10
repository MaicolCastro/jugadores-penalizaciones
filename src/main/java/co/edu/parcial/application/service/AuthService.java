package co.edu.parcial.application.service;

import co.edu.parcial.domain.exception.BadRequestException;
import co.edu.parcial.domain.exception.ConflictException;
import co.edu.parcial.domain.exception.NotFoundException;
import co.edu.parcial.domain.exception.UnauthorizedException;
import co.edu.parcial.domain.model.AppUser;
import co.edu.parcial.domain.model.Role;
import co.edu.parcial.domain.ports.in.AuthUseCase;
import co.edu.parcial.domain.ports.out.JwtPort;
import co.edu.parcial.domain.ports.out.PasswordHasherPort;
import co.edu.parcial.domain.ports.out.UserRepositoryPort;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public class AuthService implements AuthUseCase {
    private final UserRepositoryPort userRepository;
    private final PasswordHasherPort passwordHasher;
    private final JwtPort jwtPort;

    public AuthService(UserRepositoryPort userRepository, PasswordHasherPort passwordHasher, JwtPort jwtPort) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtPort = jwtPort;
    }

    @Override
    public Mono<TokenPair> login(LoginCommand command) {
        return userRepository.findByUsername(command.username())
                .switchIfEmpty(Mono.error(new UnauthorizedException("Credenciales inválidas")))
                .flatMap(user -> {
                    if (!passwordHasher.matches(command.password(), user.passwordHash())) {
                        return Mono.error(new UnauthorizedException("Credenciales inválidas"));
                    }
                    var token = jwtPort.generateAccessToken(user);
                    return Mono.just(new TokenPair(token, "Bearer"));
                });
    }

    @Override
    public Mono<AppUser> register(RegisterCommand command) {
        String username = command.username().trim().toLowerCase(Locale.ROOT);
        Role role;
        try {
            role = Role.valueOf(command.role().trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return Mono.error(new BadRequestException("role debe ser ADMIN o USER"));
        }

        return userRepository.existsByUsername(username)
                .flatMap(exists -> exists
                        ? Mono.<AppUser>error(new ConflictException("El username ya existe"))
                        : Mono.just(new AppUser(
                                UUID.randomUUID(),
                                username,
                                passwordHasher.hash(command.password()),
                                role,
                                Instant.now()
                        )))
                .flatMap(userRepository::save);
    }
}

