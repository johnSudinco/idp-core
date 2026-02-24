package com.idp_core.idp_core.infrastructure.adapter.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "error_logs", schema = "audit")
@Data
public class ErrorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;
    private String service;
    private String message;

    @Column(columnDefinition = "TEXT")
    private String exception;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> context;

    private String correlationId;
    private LocalDateTime createdAt;
}

