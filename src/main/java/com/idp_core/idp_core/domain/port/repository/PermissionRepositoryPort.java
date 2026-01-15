package com.idp_core.idp_core.domain.port.repository;


import com.idp_core.idp_core.domain.model.Permission;

import java.util.Optional;

public interface PermissionRepositoryPort {

    Optional<Permission> findById(Long id);

    Optional<Permission> findByName(String name);
}
