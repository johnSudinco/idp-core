package com.idp_core.idp_core.infrastructure.adapter.repository.jpa;

import com.idp_core.idp_core.infrastructure.adapter.entities.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuditLogRepository extends JpaRepository<AuditLogJpaEntity, Long> {}

