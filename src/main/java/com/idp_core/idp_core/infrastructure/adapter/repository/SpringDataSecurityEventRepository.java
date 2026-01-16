package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.SecurityEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSecurityEventRepository extends JpaRepository<SecurityEventEntity, Long> {
}
