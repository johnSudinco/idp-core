package com.idp_core.idp_core.domain.port.repository;



import com.idp_core.idp_core.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepositoryPort {
    void save(PasswordResetToken token);
    Optional<PasswordResetToken> findByToken(String token);
}
