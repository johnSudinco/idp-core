package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.RolePermission;

import java.util.List;

public interface RolePermissionRepositoryPort {

    boolean exists(Long roleId, Long permissionId);

    void save(RolePermission rolePermission);

    void delete(Long roleId, Long permissionId);


    List<String> findPermissionNamesByUserId(Long userId);


}
