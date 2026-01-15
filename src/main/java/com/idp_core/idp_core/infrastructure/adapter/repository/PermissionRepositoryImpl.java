package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Permission;
import com.idp_core.idp_core.domain.port.repository.PermissionRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermissionRepositoryImpl implements PermissionRepositoryPort {

    private final JpaPermissionRepository repository;

    public PermissionRepositoryImpl(JpaPermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return repository.findByName(name);
    }
}
