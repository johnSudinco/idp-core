package com.idp_core.idp_core.domain.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "security_events", schema = "audit")
@Data
public class SecurityEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String eventType;
    private Long clientId;
    private String ipAddress;
    private String userAgent;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> details;

    private LocalDateTime createdAt;
}
