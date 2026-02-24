package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.infrastructure.adapter.entities.RoleEntity;

public class RoleMapper {

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;
        return new Role(entity.getName());
    }

    public static RoleEntity toEntity(Role role) {
        if (role == null) return null;
        RoleEntity entity = new RoleEntity();
        entity.setName(role.getName());
        return entity;
    }
}