package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.domain.port.repository.AuditLogServicePort;
import org.springframework.stereotype.Service;

@Service
public class LogAuditEventUseCase {

    private final AuditLogServicePort auditLogServicePort;

    public LogAuditEventUseCase(AuditLogServicePort auditLogServicePort) {
        this.auditLogServicePort = auditLogServicePort;
    }

    public void execute(AuditLog auditLog) {
        auditLogServicePort.log(auditLog);
    }
}
