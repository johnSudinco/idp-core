package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.infrastructure.adapter.entities.ClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.RefreshTokenEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class TokenMapper {

    public RefreshTokenEntity toEntity(Token token) {
        if (token == null) return null;
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(token.getId());
        entity.setToken(token.getToken());
        entity.setClientId(token.getClient().getId()); //  usar clientId
        entity.setUserId(token.getUser().getId());     //  usar userId
        entity.setCreatedAt(token.getIssuedAt().toInstant(ZoneOffset.UTC)); // si usas LocalDateTime en dominio
        entity.setExpiresAt(token.getExpiresAt().toInstant(ZoneOffset.UTC));
        entity.setRevokedAt(token.getRevokedAt() != null ? token.getRevokedAt().toInstant(ZoneOffset.UTC) : null);
        return entity;
    }

    public Token toDomain(RefreshTokenEntity entity) {
        if (entity == null) return null;
        return new Token(
                entity.getId(),
                entity.getToken(),
                new User(entity.getUserId()),   //  reconstruir dominio con ID
                new Client(entity.getClientId()), //  reconstruir dominio con ID
                entity.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                entity.getExpiresAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                entity.getRevokedAt() != null ? entity.getRevokedAt().atZone(ZoneOffset.UTC).toLocalDateTime() : null
        );
    }
}

