package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.ErrorLog;
import com.idp_core.idp_core.domain.port.repository.ErrorLogRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LogErrorEventUseCase {

    private final ErrorLogRepositoryPort repository;

    public LogErrorEventUseCase(ErrorLogRepositoryPort repository) {
        this.repository = repository;
    }

    public void execute(ErrorLog errorLog) {
        repository.save(errorLog);
    }
}
