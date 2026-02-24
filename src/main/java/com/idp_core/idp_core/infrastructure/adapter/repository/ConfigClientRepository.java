package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.infrastructure.adapter.entities.ConfigClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigClientRepository extends JpaRepository<ConfigClientEntity, Long> {
    Optional<ConfigClientEntity> findByClientId(String clientId);
    boolean existsByClientId(String clientId);
}
