package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaSessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByUserIdAndTokenHash(Long userId, String tokenHash);
}
