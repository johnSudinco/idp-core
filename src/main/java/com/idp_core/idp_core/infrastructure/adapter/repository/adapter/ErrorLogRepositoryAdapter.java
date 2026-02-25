package com.idp_core.idp_core.infrastructure.adapter.repository.adapter;

import com.idp_core.idp_core.domain.model.ErrorLog;
import com.idp_core.idp_core.domain.port.repository.ErrorLogRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.ErrorLogEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.SpringDataErrorLogRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ErrorLogRepositoryAdapter implements ErrorLogRepositoryPort {

    private final SpringDataErrorLogRepository jpaRepository;

    public ErrorLogRepositoryAdapter(SpringDataErrorLogRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ErrorLog save(ErrorLog log) {
        ErrorLogEntity entity = mapToEntity(log);
        return mapToDomain(jpaRepository.save(entity));
    }

    private ErrorLogEntity mapToEntity(ErrorLog log) {
        if (log == null) return null;

        ErrorLogEntity entity = new ErrorLogEntity();
        entity.setId(log.getId());
        entity.setLevel(log.getLevel());
        entity.setService(log.getService());
        entity.setMessage(log.getMessage());
        entity.setException(log.getException());
        entity.setContext(log.getContext());
        entity.setCorrelationId(log.getCorrelationId());
        entity.setCreatedAt(log.getCreatedAt());

        return entity;
    }

    private ErrorLog mapToDomain(ErrorLogEntity entity) {
        if (entity == null) return null;

        // Aquí usamos el builder de Lombok
        return ErrorLog.builder()
                .id(entity.getId())
                .level(entity.getLevel())
                .service(entity.getService())
                .message(entity.getMessage())
                .exception(entity.getException())
                .context(entity.getContext())
                .correlationId(entity.getCorrelationId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}