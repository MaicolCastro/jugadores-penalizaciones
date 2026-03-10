package co.edu.parcial.adapters.out.security;

import co.edu.parcial.config.JwtProperties;
import co.edu.parcial.domain.model.AppUser;
import co.edu.parcial.domain.model.Role;
import co.edu.parcial.domain.ports.out.JwtPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtAdapter implements JwtPort {
    public static final String CLAIM_ROLE = "role";

    private final JwtProperties props;
    private final SecretKey key;

    public JwtAdapter(JwtProperties props) {
        this.props = props;
        // Por defecto: texto plano. Si se quiere base64, usar prefijo "base64:".
        String secret = props.secret() == null ? "" : props.secret();
        byte[] bytes = secret.startsWith("base64:")
                ? Decoders.BASE64.decode(secret.substring("base64:".length()))
                : secret.getBytes();
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String generateAccessToken(AppUser user) {
        Instant now = Instant.now();
        Instant exp = now.plus(props.accessTokenTtlMinutes(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(user.username())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claims(Map.of(CLAIM_ROLE, user.role().name()))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(props.issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Role roleFromClaims(Claims claims) {
        Object r = claims.get(CLAIM_ROLE);
        return Role.valueOf(String.valueOf(r));
    }
}

