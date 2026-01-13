package com.idp_core.idp_core.infrastructure.adapter.repository;


import com.idp_core.idp_core.domain.model.UserRole;
import com.idp_core.idp_core.domain.port.repository.UserRoleRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class UserRoleRepositoryImpl implements UserRoleRepositoryPort {

    private final JpaUserRoleRepository repository;

    public UserRoleRepositoryImpl(JpaUserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean exists(Long userId, Long roleId) {
        return repository.existsByIdUserIdAndIdRoleId(userId, roleId);
    }

    @Override
    public void save(UserRole userRole) {
        repository.save(userRole);
    }

    @Override
    public void delete(Long userId, Long roleId) {
        repository.deleteByIdUserIdAndIdRoleId(userId, roleId);
    }
}
