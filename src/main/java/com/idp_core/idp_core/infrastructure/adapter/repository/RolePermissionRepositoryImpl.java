package com.idp_core.idp_core.infrastructure.adapter.repository;


import com.idp_core.idp_core.domain.model.RolePermission;
import com.idp_core.idp_core.domain.port.repository.RolePermissionRepositoryPort;
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
        repository.save(rolePermission);
    }

    @Override
    public void delete(Long roleId, Long permissionId) {
        repository.deleteByIdRoleIdAndIdPermissionId(roleId, permissionId);
    }
    @Override
    public List<String> findPermissionNamesByUserId(Long userId) {
        return repository.findPermissionNamesByUserId(userId);
    }
}
