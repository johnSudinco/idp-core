package com.idp_core.idp_core.domain.port.repository;


import com.idp_core.idp_core.domain.model.UserRole;

public interface UserRoleRepositoryPort {

    boolean exists(Long userId, Long roleId);

    void save(UserRole userRole);

    void delete(Long userId, Long roleId);
}
