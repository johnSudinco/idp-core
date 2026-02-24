package com.idp_core.idp_core.application.dto;
import java.time.LocalDateTime;
import java.util.Set;

public record ClientResponse(
        Long id,
        String clientId,
        String name,
        String type,
        Set<String> redirectUris,
        Set<String> allowedGrantTypes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClientResponse fromDomain(com.idp_core.idp_core.domain.model.Client client) {
        return new ClientResponse(
                client.getId(),
                client.getClientId(),
                client.getName(),
                client.getType(),
                client.getRedirectUris(),
                client.getAllowedGrantTypes(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
