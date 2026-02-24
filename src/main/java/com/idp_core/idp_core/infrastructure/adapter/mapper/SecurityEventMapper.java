package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.SecurityEvent;
import com.idp_core.idp_core.infrastructure.adapter.entities.SecurityEventEntity;

public class SecurityEventMapper {

    public static SecurityEventEntity toEntity(SecurityEvent event) {
        SecurityEventEntity entity = new SecurityEventEntity();
        entity.setId(event.getId());
        entity.setUserId(event.getUserId());
        entity.setEventType(event.getEventType());
        entity.setClientId(event.getClientId());
        entity.setIpAddress(event.getIpAddress());
        entity.setUserAgent(event.getUserAgent());
        entity.setDetails(event.getDetails());
        entity.setCreatedAt(event.getCreatedAt());
        return entity;
    }

    public static SecurityEvent toDomain(SecurityEventEntity entity) {
        return SecurityEvent.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .eventType(entity.getEventType())
                .clientId(entity.getClientId())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .details(entity.getDetails())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
