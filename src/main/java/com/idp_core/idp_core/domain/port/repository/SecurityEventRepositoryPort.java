package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.SecurityEvent;

public interface SecurityEventRepositoryPort {
    SecurityEvent save(SecurityEvent event);
}
