package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.infrastructure.adapter.entities.ClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientEntity toEntity(Client client) {
        if (client == null) return null;

        ClientEntity entity = new ClientEntity();
        entity.setId(client.getId());
        entity.setClientId(client.getClientId());
        entity.setClientSecretHash(client.getClientSecretHash());
        entity.setName(client.getName());
        entity.setType(client.getType());
        entity.setRedirectUris(client.getRedirectUris());
        entity.setAllowedGrantTypes(client.getAllowedGrantTypes());
        entity.setCreatedAt(client.getCreatedAt());
        entity.setUpdatedAt(client.getUpdatedAt());
        return entity;
    }

    public Client toDomain(ClientEntity entity) {
        if (entity == null) return null;

        return new Client(
                entity.getId(),
                entity.getClientId(),
                entity.getClientSecretHash(),
                entity.getName(),
                entity.getType(),
                entity.getRedirectUris(),
                entity.getAllowedGrantTypes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                null
        );
    }

}
