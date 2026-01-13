package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.AuthorizationCode;

import java.util.Optional;

public interface AuthorizationCodeRepositoryPort {
    Optional<AuthorizationCode> findByCodeHash(String codeHash);
    AuthorizationCode save(AuthorizationCode code);
    void delete(AuthorizationCode code);
}
