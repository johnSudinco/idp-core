package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Session;
import java.util.Optional;

public interface SessionRepositoryPort {
    Optional<Session> findByTokenHash(String tokenHash);
    Optional<Session> findByUserIdAndTokenHash(Long userId, String tokenHash);
    void save(Session session);
}
