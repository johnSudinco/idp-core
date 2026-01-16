package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.SecurityEvent;
import com.idp_core.idp_core.domain.model.SecurityEventEntity;
import com.idp_core.idp_core.domain.port.repository.SecurityEventRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.mapper.SecurityEventMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityEventRepositoryAdapter implements SecurityEventRepositoryPort {

    private final SpringDataSecurityEventRepository jpaRepository;

    public SecurityEventRepositoryAdapter(SpringDataSecurityEventRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public SecurityEvent save(SecurityEvent event) {
        SecurityEventEntity entity = SecurityEventMapper.toEntity(event);
        SecurityEventEntity saved = jpaRepository.save(entity);
        return SecurityEventMapper.toDomain(saved);
    }
}
