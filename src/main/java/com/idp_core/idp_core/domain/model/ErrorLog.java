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
}
