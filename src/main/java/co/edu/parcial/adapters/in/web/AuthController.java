package co.edu.parcial.adapters.in.web;

import co.edu.parcial.adapters.in.web.dto.AuthDtos;
import co.edu.parcial.domain.ports.in.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUseCase auth;

    public AuthController(AuthUseCase auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        return auth.register(new AuthUseCase.RegisterCommand(req.username(), req.password(), req.role()))
                .then();
    }

    @PostMapping("/login")
    public Mono<AuthDtos.TokenResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        return auth.login(new AuthUseCase.LoginCommand(req.username(), req.password()))
                .map(t -> new AuthDtos.TokenResponse(t.accessToken(), t.tokenType()));
    }
}

