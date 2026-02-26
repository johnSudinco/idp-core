package com.idp_core.idp_core.domain.model;

import lombok.Builder;
import lombok.Getter;
import java.time.OffsetDateTime;

@Getter
@Builder
public class UserConsent {
    private Long id;
    private Long userId;
    private String consentType;
    private String version;
    private String documentPath;
    private String documentHash;
    private String action; // 'ACCEPTED' o 'REJECTED'
    private String userAgent;
    private String ipAddress;
    private boolean isAccepted;
    private OffsetDateTime acceptedAt;
}