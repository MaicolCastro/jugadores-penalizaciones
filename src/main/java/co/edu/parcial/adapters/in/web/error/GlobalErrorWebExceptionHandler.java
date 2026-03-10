package co.edu.parcial.adapters.in.web.error;

import co.edu.parcial.domain.exception.BadRequestException;
import co.edu.parcial.domain.exception.ConflictException;
import co.edu.parcial.domain.exception.NotFoundException;
import co.edu.parcial.domain.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper mapper;

    public GlobalErrorWebExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        var response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        HttpStatus status = mapStatus(ex);
        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                messageOf(ex),
                exchange.getRequest().getPath().value(),
                detailsOf(ex)
        );

        byte[] json;
        try {
            json = mapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            json = ("{\"message\":\"Error serializando respuesta\"}").getBytes(StandardCharsets.UTF_8);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(status);
        var buffer = response.bufferFactory().wrap(json);
        return response.writeWith(Mono.just(buffer));
    }

    private static HttpStatus mapStatus(Throwable ex) {
        if (ex instanceof WebExchangeBindException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof BadRequestException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof ConflictException) return HttpStatus.CONFLICT;
        if (ex instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof UnauthorizedException) return HttpStatus.UNAUTHORIZED;
        if (ex instanceof BadCredentialsException) return HttpStatus.UNAUTHORIZED;
        if (ex instanceof AccessDeniedException) return HttpStatus.FORBIDDEN;
        if (ex instanceof ResponseStatusException rse) return HttpStatus.valueOf(rse.getStatusCode().value());
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private static String messageOf(Throwable ex) {
        if (ex instanceof WebExchangeBindException) return "Validación fallida";
        return ex.getMessage() == null ? "Error inesperado" : ex.getMessage();
    }

    private static Map<String, Object> detailsOf(Throwable ex) {
        if (ex instanceof WebExchangeBindException bind) {
            Map<String, String> fields = new HashMap<>();
            for (var err : bind.getBindingResult().getAllErrors()) {
                if (err instanceof FieldError fe) {
                    fields.put(fe.getField(), fe.getDefaultMessage());
                } else {
                    fields.put(err.getObjectName(), err.getDefaultMessage());
                }
            }
            return Map.of("fields", fields);
        }
        return null;
    }
}

