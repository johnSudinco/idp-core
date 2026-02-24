package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.infrastructure.adapter.entities.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {
}
