package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserRoleEntity;
import com.idp_core.idp_core.web.common.DataEncryptor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final DataEncryptor dataEncryptor;

    public UserMapper(DataEncryptor dataEncryptor) {
        this.dataEncryptor = dataEncryptor;
    }

    // Dominio -> Entidad (encriptar antes de persistir)
    public UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setName(dataEncryptor.encrypt(user.getName()));
        entity.setLastname(dataEncryptor.encrypt(user.getLastname()));
        entity.setStatus(user.getStatus());
        entity.setTwoFactor(user.isTwoFactor());
        entity.setIdentification(dataEncryptor.encrypt(user.getIdentification()));
        entity.setPhone(dataEncryptor.encrypt(user.getPhone()));
        entity.setAddress(dataEncryptor.encrypt(user.getAddress()));

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

    // Entidad -> Dominio (descifrar al recuperar)
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getStatus(),
                entity.isTwoFactor(),
                dataEncryptor.decrypt(entity.getName()),
                dataEncryptor.decrypt(entity.getLastname()),
                dataEncryptor.decrypt(entity.getIdentification()),
                dataEncryptor.decrypt(entity.getPhone()),
                dataEncryptor.decrypt(entity.getAddress())
        );

        entity.getRoles().forEach(userRoleEntity -> {
            Role role = new Role(
                    userRoleEntity.getRole().getId(),
                    userRoleEntity.getRole().getName()
            );
            user.addRole(role);
        });

        return user;
    }
}
