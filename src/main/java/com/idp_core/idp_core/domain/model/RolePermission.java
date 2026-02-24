package com.idp_core.idp_core.domain.model;

import com.idp_core.idp_core.infrastructure.adapter.entities.RolePermissionId;

import java.time.LocalDateTime;

public class RolePermission {

    private RolePermissionId id;
    private Role role;
    private Permission permission;
    private LocalDateTime grantedAt;

    // Constructor protegido para evitar instanciación directa sin validación
    protected RolePermission() {}

    // Constructor público para crear nuevas relaciones
    public RolePermission(Role role, Permission permission) {
        if (role == null || permission == null) {
            throw new IllegalArgumentException("Role and Permission cannot be null");
        }
        this.role = role;
        this.permission = permission;
        this.id = new RolePermissionId(role.getId(), permission.getId());
        this.grantedAt = LocalDateTime.now();
    }

    // Getters
    public RolePermissionId getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public Permission getPermission() {
        return permission;
    }

    public LocalDateTime getGrantedAt() {
        return grantedAt;
    }

    // equals & hashCode por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePermission other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
