package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.infrastructure.adapter.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPermissionRepository
        extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByName(String name);
}
