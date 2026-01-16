package com.idp_core.idp_core.domain.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class SecurityEvent {

    private Long id;
    private Long userId;
    private String eventType;
    private Long clientId;
    private String ipAddress;
    private String userAgent;
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> details;
    private LocalDateTime createdAt;
}
