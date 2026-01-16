package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.SecurityEvent;
import com.idp_core.idp_core.domain.port.repository.SecurityEventRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LogSecurityEventUseCase {

    private final SecurityEventRepositoryPort repository;

    public LogSecurityEventUseCase(SecurityEventRepositoryPort repository) {
        this.repository = repository;
    }

    public void execute(SecurityEvent event) {
        repository.save(event);
    }
}
