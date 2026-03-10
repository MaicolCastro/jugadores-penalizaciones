package co.edu.parcial.domain.ports.in;

import co.edu.parcial.domain.model.AppUser;
import reactor.core.publisher.Mono;

public interface AuthUseCase {
    Mono<TokenPair> login(LoginCommand command);
    Mono<AppUser> register(RegisterCommand command);

    record LoginCommand(String username, String password) {}
    record RegisterCommand(String username, String password, String role) {}
    record TokenPair(String accessToken, String tokenType) {}
}

