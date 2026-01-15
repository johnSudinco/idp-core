package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Session;
import com.idp_core.idp_core.domain.port.repository.SessionRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionRepositoryAdapter implements SessionRepositoryPort {

    private final JpaSessionRepository jpaRepository;

    public SessionRepositoryAdapter(JpaSessionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    @Override
    public Optional<Session> findByTokenHash(String tokenHash) {
        return jpaRepository.findByTokenHash(tokenHash);
    }
    @Override
    public Optional<Session> findByUserIdAndTokenHash(Long userId, String tokenHash) {
        return jpaRepository.findByUserIdAndTokenHash(userId, tokenHash);
    }

    @Override
    public void save(Session session) {
        jpaRepository.save(session);
    }
}
