package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.RefreshToken;
import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.port.repository.RefreshTokenRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {
        private final JpaRefreshTokenRepository jpaRepository;

        public RefreshTokenRepositoryAdapter(JpaRefreshTokenRepository jpaRepository) {
            this.jpaRepository = jpaRepository;
        }

        @Override
        public Optional<RefreshToken> findByTokenHash(String tokenHash) {
            return jpaRepository.findByTokenHash(tokenHash)
                    .map(entity -> {
                        RefreshToken rt = new RefreshToken();
                        rt.setId(entity.getId());
                        rt.setUserId(entity.getUser().getId());
                        rt.setClientId(entity.getClient().getId());
                        rt.setTokenHash(entity.getTokenHash());
                        // issuedAt, expiresAt, revokedAt → si están en la tabla, añádelos en la entidad y mapea aquí
                        return rt;
                    });
        }

        @Override
        public void save(RefreshToken refreshToken) {
            Token entity = new Token();
            entity.setId(refreshToken.getId());
            entity.setTokenHash(refreshToken.getTokenHash());
            // aquí necesitas setear el User y Client completos, no solo los IDs
            // por ejemplo: entity.setUser(userRepository.getReferenceById(refreshToken.getUserId()));
            // entity.setClient(clientRepository.getReferenceById(refreshToken.getClientId()));
            jpaRepository.save(entity);
        }
    }

