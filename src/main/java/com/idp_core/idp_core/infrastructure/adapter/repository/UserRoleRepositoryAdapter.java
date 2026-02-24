package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.UserRole;
import com.idp_core.idp_core.domain.model.UserRoleId;
import com.idp_core.idp_core.domain.port.repository.UserRoleRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryAdapter implements UserRoleRepositoryPort {

    private final JpaUserRoleRepository repository;

    public UserRoleRepositoryAdapter(JpaUserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(UserRoleId id) {
        return repository.existsByUserIdAndRoleId(id.getUserId(), id.getRoleId());
    }

    @Override
    public void save(UserRole userRole) {
        UserRoleEntity entity = new UserRoleEntity(
                new UserEntity(userRole.getUser().getId()),
                new RoleEntity(userRole.getRole().getId())
        );
        repository.save(entity);
    }

    @Override
    public void deleteById(UserRoleId id) {
        repository.deleteByUserIdAndRoleId(id.getUserId(), id.getRoleId());
    }
}

