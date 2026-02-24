package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Session;
import com.idp_core.idp_core.domain.port.repository.SessionRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.SessionEntity;
import com.idp_core.idp_core.infrastructure.adapter.mapper.SessionMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SessionRepositoryAdapter implements SessionRepositoryPort {

    private final JpaSessionRepository jpaRepository;
    private final SessionMapper mapper;

    public SessionRepositoryAdapter(JpaSessionRepository jpaRepository, SessionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Session> findByTokenHash(String tokenHash) {
        return jpaRepository.findByTokenHash(tokenHash)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Session> findByUserIdAndTokenHash(Long userId, String tokenHash) {
        return jpaRepository.findByUserIdAndTokenHash(userId, tokenHash)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Session session) {
        jpaRepository.save(mapper.toEntity(session));
    }

}
