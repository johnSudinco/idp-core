package com.idp_core.idp_core.domain.model;

import com.idp_core.idp_core.application.port.TokenHashService;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class RefreshTokenFactory {
    public static Token create(Long userId, Claims claims, String refreshToken) {
        LocalDateTime issuedAt = claims.getIssuedAt().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime expiresAt = claims.getExpiration().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();

        return new Token(
                null,
                refreshToken,   //  se guarda el token directamente
                new User(userId),
                new Client(Long.parseLong(claims.get("client_id").toString())),
                issuedAt,
                expiresAt,
                null
        );
    }
}

