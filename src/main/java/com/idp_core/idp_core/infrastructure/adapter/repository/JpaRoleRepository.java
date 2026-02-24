package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
