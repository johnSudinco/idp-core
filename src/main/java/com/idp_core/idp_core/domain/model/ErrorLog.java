package com.idp_core.idp_core.domain.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorLog {

    private Long id;
    private String level;
    private String service;
    private String message;
    private String exception;
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> context;
    private String correlationId;
    private LocalDateTime createdAt;

    public ErrorLog() {}

    // Constructor completo para compatibilidad
    public ErrorLog(Long id, String level, String service, String message, String exception,
                    Map<String, Object> context, String correlationId, LocalDateTime createdAt) {
        this.id = id;
        this.level = level;
        this.service = service;
        this.message = message;
        this.exception = exception;
        this.context = context;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
    }
}
