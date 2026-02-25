package com.idp_core.idp_core.infrastructure.adapter.repository.adapter;

import com.idp_core.idp_core.domain.model.Permission;
import com.idp_core.idp_core.domain.port.repository.PermissionRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.PermissionEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaPermissionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final JpaPermissionRepository jpaPermissionRepository;

    public PermissionRepositoryAdapter(JpaPermissionRepository jpaPermissionRepository) {
        this.jpaPermissionRepository = jpaPermissionRepository;
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return jpaPermissionRepository.findById(id)
                .map(entity -> new Permission(entity.getId(), entity.getName()));
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return jpaPermissionRepository.findByName(name)
                .map(entity -> new Permission(entity.getId(), entity.getName()));
    }

    @Override
    public Permission save(Permission permission) {
        PermissionEntity entity = new PermissionEntity();
        entity.setName(permission.getName());
        PermissionEntity saved = jpaPermissionRepository.save(entity);
        return new Permission(saved.getId(), saved.getName());
    }
}
