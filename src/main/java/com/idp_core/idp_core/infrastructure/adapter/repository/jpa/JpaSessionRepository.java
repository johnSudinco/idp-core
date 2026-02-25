package com.idp_core.idp_core.infrastructure.adapter.repository.jpa;

import com.idp_core.idp_core.infrastructure.adapter.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaSessionRepository extends JpaRepository<SessionEntity, UUID> {
    Optional<SessionEntity> findByTokenHash(String tokenHash);
    Optional<SessionEntity> findByUserIdAndTokenHash(Long userId, String tokenHash);
}