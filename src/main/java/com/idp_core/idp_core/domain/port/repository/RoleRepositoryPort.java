package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Role;
import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role save(Role role);
}
