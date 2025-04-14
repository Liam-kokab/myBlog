package no.kokab.myBlog.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import no.kokab.myBlog.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static SecretKey signingKey;

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    /**
     * Initialize the JWT utility with a custom key. For testing purposes only.
     */
    public void initWithCustomKey(String key) {
        signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key));
    }

    /**
     * Generate a JWT Token
     */
    public static String generateToken(UserEntity user) {

        return Jwts.builder()
            .claims(Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "userId", user.getUserId()
            ))
            .subject(user.getEmail())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(signingKey)
            .compact();
    }

    /**
     * Validate and parse the JWT Token
     */
    public static String extractEmail(String token) {

        try {
            Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return claims.getSubject();
        } catch (Exception e) {
            return null; // Invalid token
        }
    }

    public static Claims extractClaims(String token) {

        try {
            return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            return null;  // Invalid token
        }
    }
}

