package com.idp_core.idp_core.infrastructure.adapter.repository.jpa;

import com.idp_core.idp_core.infrastructure.adapter.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
