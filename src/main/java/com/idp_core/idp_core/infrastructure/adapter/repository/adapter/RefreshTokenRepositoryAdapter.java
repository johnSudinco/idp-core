package com.idp_core.idp_core.infrastructure.adapter.repository.adapter;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.RefreshTokenRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.RefreshTokenEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaRefreshTokenRepository;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.Optional;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final JpaRefreshTokenRepository repository;

    public RefreshTokenRepositoryAdapter(JpaRefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Token save(Token token) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(token.getId());
        entity.setToken(token.getToken()); //  campo correcto
        entity.setClientId(token.getClient().getId()); //  usar clientId
        entity.setUserId(token.getUser().getId());     //  usar userId
        entity.setCreatedAt(token.getIssuedAt().toInstant(ZoneOffset.UTC));
        entity.setExpiresAt(token.getExpiresAt().toInstant(ZoneOffset.UTC));
        entity.setRevokedAt(token.getRevokedAt() != null ? token.getRevokedAt().toInstant(ZoneOffset.UTC) : null);

        RefreshTokenEntity saved = repository.save(entity);

        return new Token(
                saved.getId(),
                saved.getToken(),
                new User(saved.getUserId()),
                new Client(saved.getClientId()),
                saved.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                saved.getExpiresAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                saved.getRevokedAt() != null ? saved.getRevokedAt().atZone(ZoneOffset.UTC).toLocalDateTime() : null
        );
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token)
                .map(entity -> new Token(
                        entity.getId(),
                        entity.getToken(),
                        new User(entity.getUserId()),
                        new Client(entity.getClientId()),
                        entity.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                        entity.getExpiresAt().atZone(ZoneOffset.UTC).toLocalDateTime(),
                        entity.getRevokedAt() != null ? entity.getRevokedAt().atZone(ZoneOffset.UTC).toLocalDateTime() : null
                ));
    }

    @Override
    public void delete(Token token) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(token.getId());
        entity.setToken(token.getToken());
        entity.setClientId(token.getClient().getId());
        entity.setUserId(token.getUser().getId());
        entity.setCreatedAt(token.getIssuedAt().toInstant(ZoneOffset.UTC));
        entity.setExpiresAt(token.getExpiresAt().toInstant(ZoneOffset.UTC));
        entity.setRevokedAt(token.getRevokedAt() != null ? token.getRevokedAt().toInstant(ZoneOffset.UTC) : null);

        repository.delete(entity);
    }
}
