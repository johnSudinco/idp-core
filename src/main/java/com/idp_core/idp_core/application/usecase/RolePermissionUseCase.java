package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.Permission;
import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.RolePermission;
import com.idp_core.idp_core.domain.port.repository.PermissionRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.RolePermissionRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.RoleRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



@Service
public class RolePermissionUseCase {

    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final RolePermissionRepositoryPort rolePermissionRepository;

    public RolePermissionUseCase(
            RoleRepositoryPort roleRepository,
            PermissionRepositoryPort permissionRepository,
            RolePermissionRepositoryPort rolePermissionRepository
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Transactional
    public void grantPermission(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no existe"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permiso no existe"));

        if (rolePermissionRepository.exists(roleId, permissionId)) {
            return;
        }

        rolePermissionRepository.save(new RolePermission(role, permission));
    }

    @Transactional
    public void revokePermission(Long roleId, Long permissionId) {
        rolePermissionRepository.delete(roleId, permissionId);
    }
}
