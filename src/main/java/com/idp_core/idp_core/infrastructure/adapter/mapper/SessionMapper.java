package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.Session;
import com.idp_core.idp_core.infrastructure.adapter.entities.SessionEntity;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    public SessionEntity toEntity(Session session) {
        if (session == null) return null;

        return new SessionEntity(
                session.getUserId(),
                session.getClientId(),
                session.getTokenHash(),
                session.getIpAddress(),
                session.getUserAgent()
        );
    }

    public Session toDomain(SessionEntity entity) {
        if (entity == null) return null;

        return Session.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .clientId(entity.getClientId())
                .tokenHash(entity.getTokenHash())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .createdAt(entity.getCreatedAt())
                .terminatedAt(entity.getTerminatedAt())
                .build();
    }
}
