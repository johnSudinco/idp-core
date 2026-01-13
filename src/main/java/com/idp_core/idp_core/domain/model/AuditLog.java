package com.idp_core.idp_core.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AuditLog {
    private Long actorUserId;
    private String action;
    private String targetType;
    private Long targetId;
    private String correlationId;
    private String ipAddress;
    private String userAgent;
    private String metadata;
    private LocalDateTime createdAt;

    // Getters, setters, constructor
}

