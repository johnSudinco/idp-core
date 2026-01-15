package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.UserRole;
import com.idp_core.idp_core.domain.model.UserRoleId;

public interface UserRoleRepositoryPort {

    boolean existsById(UserRoleId id);

    void save(UserRole userRole);

    void deleteById(UserRoleId id);
}
