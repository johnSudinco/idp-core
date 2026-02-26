package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_consents", schema = "auth")
@Data
public class UserConsentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "consent_type", length = 50, nullable = false)
    private String consentType;

    @Column(length = 20, nullable = false)
    private String version;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_hash", length = 64)
    private String documentHash;

    @Column(length = 20)
    private String action;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    @Column(name = "accepted_at")
    private OffsetDateTime acceptedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}