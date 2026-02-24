package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.infrastructure.adapter.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientId(String clientId);
    boolean existsByClientId(String clientId);
}
