package com.idp_core.idp_core.infrastructure.adapter.mapper;

import com.idp_core.idp_core.domain.model.ErrorLog;
import com.idp_core.idp_core.infrastructure.adapter.entities.ErrorLogEntity;

public class ErrorLogMapper {

    public static ErrorLogEntity toEntity(ErrorLog log) {
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

    public static ErrorLog toDomain(ErrorLogEntity entity) {
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
