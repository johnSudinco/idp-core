package com.idp_core.idp_core.infrastructure.adapter.repository;


import com.idp_core.idp_core.domain.model.RolePermission;
import com.idp_core.idp_core.domain.model.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaRolePermissionRepository
        extends JpaRepository<RolePermission, RolePermissionId> {

    boolean existsByIdRoleIdAndIdPermissionId(Long roleId, Long permissionId);

    void deleteByIdRoleIdAndIdPermissionId(Long roleId, Long permissionId);

    @Query("""
        select p.name
        from UserRole ur
        join RolePermission rp on rp.role.id = ur.role.id
        join Permission p on p.id = rp.permission.id
        where ur.user.id = :userId
    """)
    List<String> findPermissionNamesByUserId(Long userId);
}
