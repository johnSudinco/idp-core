package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.UserRole;
import com.idp_core.idp_core.domain.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    boolean existsByIdUserIdAndIdRoleId(Long userId, Long roleId);

    void deleteByIdUserIdAndIdRoleId(Long userId, Long roleId);
}
