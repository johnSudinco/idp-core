package com.idp_core.idp_core.infrastructure.adapter.repository.impl;


import com.idp_core.idp_core.domain.model.PasswordResetToken;
import com.idp_core.idp_core.domain.port.repository.PasswordResetTokenRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.PasswordResetTokenEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaPasswordResetTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepositoryPort {

    private final JpaPasswordResetTokenRepository jpaRepository;

    public PasswordResetTokenRepositoryImpl(JpaPasswordResetTokenRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(PasswordResetToken token) {
        PasswordResetTokenEntity entity = toEntity(token);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return jpaRepository.findByToken(token).map(this::toDomain);
    }

    // --- Mappers ---
    private PasswordResetTokenEntity toEntity(PasswordResetToken token) {
        PasswordResetTokenEntity entity = new PasswordResetTokenEntity();
        entity.setId(token.getId());
        entity.setUserId(token.getUserId());
        entity.setToken(token.getToken());
        entity.setExpiresAt(token.getExpiresAt());
        entity.setUsedAt(token.getUsedAt());
        return entity;
    }

    private PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        PasswordResetToken token = new PasswordResetToken(
                entity.getUserId(),
                entity.getToken(),
                entity.getExpiresAt()
        );
        token.setId(entity.getId());
        token.setUsedAt(entity.getUsedAt());
        return token;
    }
}

