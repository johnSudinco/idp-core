package com.idp_core.idp_core.domain.port.repository;


import com.idp_core.idp_core.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void save(RefreshToken refreshToken);
}
