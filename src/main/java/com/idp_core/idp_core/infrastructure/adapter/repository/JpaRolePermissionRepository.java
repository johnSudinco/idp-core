package com.idp_core.idp_core.infrastructure.adapter.repository;


import com.idp_core.idp_core.infrastructure.adapter.entities.RolePermissionEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaRolePermissionRepository
        extends JpaRepository<RolePermissionEntity, RolePermissionId> {

    boolean existsByIdRoleIdAndIdPermissionId(Long roleId, Long permissionId);

    void deleteByIdRoleIdAndIdPermissionId(Long roleId, Long permissionId);

    @Query("""
select p.name
from UserRoleEntity ur
join ur.role r
join RolePermissionEntity rp on rp.role = r
join rp.permission p
where ur.user.id = :userId
""")
    List<String> findPermissionNamesByUserId(Long userId);

}