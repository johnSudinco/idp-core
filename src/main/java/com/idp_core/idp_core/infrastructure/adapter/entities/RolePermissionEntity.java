package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "role_permissions", schema = "auth")
public class RolePermissionEntity {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;

    protected RolePermissionEntity() {}

    public RolePermissionEntity(RoleEntity role, PermissionEntity permission, LocalDateTime grantedAt) {
        this.role = role;
        this.permission = permission;
        this.id = new RolePermissionId(role.getId(), permission.getId());
        this.grantedAt = grantedAt;
    }

    // getters...
}
