package co.edu.parcial.adapters.in.web.security;

import co.edu.parcial.adapters.out.security.JwtAdapter;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtAdapter jwt;

    public JwtReactiveAuthenticationManager(JwtAdapter jwt) {
        this.jwt = jwt;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = String.valueOf(authentication.getCredentials());
        try {
            var claims = jwt.parseAndValidate(token);
            var role = JwtAdapter.roleFromClaims(claims);
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
            return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities));
        } catch (JwtException | IllegalArgumentException e) {
            return Mono.error(new BadCredentialsException("Token inválido"));
        }
    }
}

