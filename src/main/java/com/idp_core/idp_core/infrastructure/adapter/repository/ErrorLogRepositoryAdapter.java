package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.ErrorLog;
import com.idp_core.idp_core.domain.model.ErrorLogEntity;
import com.idp_core.idp_core.domain.port.repository.ErrorLogRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.mapper.ErrorLogMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ErrorLogRepositoryAdapter implements ErrorLogRepositoryPort {

    private final SpringDataErrorLogRepository jpaRepository;

    public ErrorLogRepositoryAdapter(SpringDataErrorLogRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ErrorLog save(ErrorLog errorLog) {
        ErrorLogEntity entity = ErrorLogMapper.toEntity(errorLog);
        ErrorLogEntity saved = jpaRepository.save(entity);
        return ErrorLogMapper.toDomain(saved);
    }
}
