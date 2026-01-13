package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs", schema = "audit")
@Getter
@Setter
public class AuditLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long actorUserId;
    private String action;
    private String targetType;
    private Long targetId;
    private String correlationId;
    private String ipAddress;
    private String userAgent;
    private String metadata;
    private LocalDateTime createdAt;
}
