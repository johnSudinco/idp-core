package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.ErrorLog;

public interface ErrorLogRepositoryPort {
    ErrorLog save(ErrorLog errorLog);
}
