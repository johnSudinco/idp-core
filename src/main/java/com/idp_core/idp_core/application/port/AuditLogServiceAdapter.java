package com.idp_core.idp_core.application.port;

import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.domain.port.repository.AuditLogServicePort;
import com.idp_core.idp_core.infrastructure.adapter.entities.AuditLogJpaEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.JpaAuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceAdapter implements AuditLogServicePort {

    private final JpaAuditLogRepository repository;

    public AuditLogServiceAdapter(JpaAuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void log(AuditLog auditLog) {
        AuditLogJpaEntity entity = new AuditLogJpaEntity();
        entity.setActorUserId(auditLog.getActorUserId());
        entity.setAction(auditLog.getAction());
        entity.setTargetType(auditLog.getTargetType());
        entity.setTargetId(auditLog.getTargetId());
        entity.setCorrelationId(auditLog.getCorrelationId());
        entity.setIpAddress(auditLog.getIpAddress());
        entity.setUserAgent(auditLog.getUserAgent());
        entity.setMetadata(auditLog.getMetadata());
        entity.setCreatedAt(auditLog.getCreatedAt());

        repository.save(entity);
    }
}
