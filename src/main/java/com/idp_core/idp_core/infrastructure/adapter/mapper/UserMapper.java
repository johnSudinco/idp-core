package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserRoleEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Dominio -> Entidad
    public UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setName(user.getName());
        entity.setLastname(user.getLastname());

        entity.setRoles(
                user.getRoles().stream()
                        .map(role -> {
                            RoleEntity roleEntity = new RoleEntity();
                            roleEntity.setName(role.getRoleName());

                            UserRoleEntity userRoleEntity = new UserRoleEntity();
                            userRoleEntity.setRole(roleEntity);
                            userRoleEntity.setUser(entity);
                            return userRoleEntity;
                        })
                        .collect(Collectors.toSet())
        );

        return entity;
    }

    // Entidad -> Dominio
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = User.create(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                false,
                entity.getName(),
                entity.getLastname(),
                null,
                null,
                null
        );

        entity.getRoles().forEach(userRoleEntity -> {

            Role role = new Role(
                    userRoleEntity.getRole().getId(),
                    userRoleEntity.getRole().getName()
            );

            user.addRole(role); // ← esto ya crea el UserRole internamente
        });

        return user;
    }
}