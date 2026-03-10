package co.edu.parcial.config;

import co.edu.parcial.adapters.in.web.security.BearerTokenServerAuthenticationConverter;
import co.edu.parcial.adapters.in.web.security.JwtReactiveAuthenticationManager;
import co.edu.parcial.adapters.out.security.JwtAdapter;
import co.edu.parcial.adapters.in.web.error.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Configuration
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, JwtAdapter jwt, ObjectMapper mapper) {
        AuthenticationWebFilter bearerAuth = new AuthenticationWebFilter(new JwtReactiveAuthenticationManager(jwt));
        bearerAuth.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter());
        bearerAuth.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((exchange, e) ->
                                writeError(exchange, mapper, HttpStatus.UNAUTHORIZED, "No autenticado"))
                        .accessDeniedHandler((exchange, e) ->
                                writeError(exchange, mapper, HttpStatus.FORBIDDEN, "Acceso denegado"))
                )
                .authorizeExchange(ex -> ex
                        .pathMatchers("/actuator/health", "/actuator/info").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(bearerAuth, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private static Mono<Void> writeError(org.springframework.web.server.ServerWebExchange exchange,
                                        ObjectMapper mapper,
                                        HttpStatus status,
                                        String message) {
        var response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            var body = new ApiError(
                    Instant.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    message,
                    exchange.getRequest().getPath().value(),
                    null
            );
            byte[] json = mapper.writeValueAsBytes(body);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(json)));
        } catch (Exception ex) {
            return response.setComplete();
        }
    }
}

