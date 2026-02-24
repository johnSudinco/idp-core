package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.AuthorizationCode;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.infrastructure.adapter.entities.AuthorizationCodeEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.ClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationCodeMapper {

    private final UserMapper userMapper;
    private final ClientMapper clientMapper;

    public AuthorizationCodeMapper(UserMapper userMapper, ClientMapper clientMapper) {
        this.userMapper = userMapper;
        this.clientMapper = clientMapper;
    }

    // Convierte de dominio -> entity
    public AuthorizationCodeEntity toEntity(AuthorizationCode code) {
        if (code == null) return null;

        UserEntity userEntity = userMapper.toEntity(code.getUser());
        ClientEntity clientEntity = clientMapper.toEntity(code.getClient());

        AuthorizationCodeEntity entity = new AuthorizationCodeEntity();
        entity.setCodeHash(code.getCodeHash());
        entity.setUser(userEntity);
        entity.setClient(clientEntity);
        entity.setRedirectUri(code.getRedirectUri());
        entity.setExpiresAt(code.getExpiresAt());
        entity.setUsedAt(code.getUsedAt()); //  mapeo usedAt

        return entity;
    }

    // Convierte de entity -> dominio
    public AuthorizationCode toDomain(AuthorizationCodeEntity entity) {
        if (entity == null) return null;

        User userDomain = userMapper.toDomain(entity.getUser());
        Client clientDomain = clientMapper.toDomain(entity.getClient());

        AuthorizationCode code = new AuthorizationCode(
                entity.getCodeHash(),
                userDomain,
                clientDomain,
                entity.getRedirectUri(),
                entity.getExpiresAt()
        );
        code.setUsedAt(entity.getUsedAt()); //  asignar usedAt al dominio

        return code;
    }
}