package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.port.repository.RoleRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository jpaRoleRepository;

    public RoleRepositoryAdapter(JpaRoleRepository jpaRoleRepository) {
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRoleRepository.findById(id)
                .map(entity -> new Role(entity.getId(), entity.getName()));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name)
                .map(entity -> new Role(entity.getId(), entity.getName()));
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setName(role.getName());
        RoleEntity saved = jpaRoleRepository.save(entity);
        return new Role(saved.getId(), saved.getName());
    }
}

