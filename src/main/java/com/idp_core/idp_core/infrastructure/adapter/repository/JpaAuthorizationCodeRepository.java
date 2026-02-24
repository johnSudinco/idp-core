package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.infrastructure.adapter.entities.AuthorizationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAuthorizationCodeRepository extends JpaRepository<AuthorizationCodeEntity, Long> {
    Optional<AuthorizationCodeEntity> findByCodeHash(String codeHash);
    void deleteByCodeHash(String codeHash);
}