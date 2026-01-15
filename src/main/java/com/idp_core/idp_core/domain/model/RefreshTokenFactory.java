package com.idp_core.idp_core.domain.model;

import com.idp_core.idp_core.application.port.TokenHashService;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class RefreshTokenFactory {

    public static RefreshToken create(
            Long userId,
            Claims claims,
            String rawToken,
            TokenHashService hashService
    ) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setClientId(claims.get("clientId", Long.class));
        token.setIssuedAt(LocalDateTime.now());
        token.setExpiresAt(
                claims.getExpiration().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );
        token.setTokenHash(hashService.hash(rawToken));
        return token;
    }
}
