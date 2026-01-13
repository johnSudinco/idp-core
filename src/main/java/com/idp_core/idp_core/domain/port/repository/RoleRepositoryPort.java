package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositoryPort extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
