package com.idp_core.idp_core.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Session {
    private Long id;
    private Long userId;
    private Long clientId;
    private String tokenHash;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private LocalDateTime terminatedAt;

    public boolean isTerminated() {
        return terminatedAt != null;
    }

    public void terminate() {
        if (this.terminatedAt == null) {
            this.terminatedAt = LocalDateTime.now();
        }
    }
}
