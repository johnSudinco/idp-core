package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPermissionRepository
        extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
}
