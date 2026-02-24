package com.idp_core.idp_core.domain.model;

import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class RefreshTokenFactory {

    public static Token create(Long userId, Claims claims, String refreshToken) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo al crear el refresh token");
        }

        if (claims == null) {
            throw new IllegalArgumentException("Claims no puede ser nulo");
        }

        LocalDateTime issuedAt = claims.getIssuedAt()
                .toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        LocalDateTime expiresAt = claims.getExpiration()
                .toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        // Validar client_id en claims
        Object clientIdClaim = claims.get("client_id");
        if (clientIdClaim == null) {
            throw new IllegalArgumentException("El claim 'client_id' es requerido para crear el refresh token");
        }

        Long clientId = Long.parseLong(clientIdClaim.toString());

        return new Token(
                null,                 // id generado al persistir
                refreshToken,         // se guarda el token directamente
                new User(userId),     // usuario referenciado por id
                new Client(clientId), // cliente referenciado por id
                issuedAt,
                expiresAt,
                null                  // scopes o metadata adicional
        );
    }
}
