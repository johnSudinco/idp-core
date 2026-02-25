package com.idp_core.idp_core.infrastructure.adapter.repository.jpa;


import com.idp_core.idp_core.infrastructure.adapter.entities.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    Optional<PasswordResetTokenEntity> findByToken(String token);
}
