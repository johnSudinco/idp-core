package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.AuditLog;

public interface AuditLogServicePort {
    void log(AuditLog auditLog);
}

