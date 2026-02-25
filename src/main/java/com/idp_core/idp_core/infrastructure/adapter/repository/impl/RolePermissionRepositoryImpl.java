package com.idp_core.idp_core.infrastructure.adapter.repository.impl;

import com.idp_core.idp_core.domain.model.RolePermission;
import com.idp_core.idp_core.domain.port.repository.RolePermissionRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.PermissionEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.RolePermissionEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaRolePermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionRepositoryImpl implements RolePermissionRepositoryPort {

    private final JpaRolePermissionRepository repository;

    public RolePermissionRepositoryImpl(JpaRolePermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean exists(Long roleId, Long permissionId) {
        return repository.existsByIdRoleIdAndIdPermissionId(roleId, permissionId);
    }

    @Override
    public void save(RolePermission rolePermission) {
        RolePermissionEntity entity = mapToEntity(rolePermission);
        repository.save(entity);
    }

    @Override
    public void delete(Long roleId, Long permissionId) {
        repository.deleteByIdRoleIdAndIdPermissionId(roleId, permissionId);
    }

    @Override
    public List<String> findPermissionNamesByUserId(Long userId) {
        return repository.findPermissionNamesByUserId(userId);
    }

    // --- Mapeo del dominio a la entidad ---
    private RolePermissionEntity mapToEntity(RolePermission rp) {
        return new RolePermissionEntity(
                new RoleEntity(rp.getRole().getId()),           // construyes RoleEntity con el id
                new PermissionEntity(rp.getPermission().getId()), // construyes PermissionEntity con el id
                rp.getGrantedAt()
        );
    }

    // Opcional: mapear de vuelta a dominio
    // private RolePermission mapToDomain(RolePermissionEntity entity) {
    //     Role role = new Role(entity.getRole().getId());
    //     Permission perm = new Permission(entity.getPermission().getId());
    //     return new RolePermission(role, perm);
    // }
}

