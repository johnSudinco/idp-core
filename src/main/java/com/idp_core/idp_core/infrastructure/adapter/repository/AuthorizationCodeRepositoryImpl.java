package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.AuthorizationCode;
import com.idp_core.idp_core.domain.port.repository.AuthorizationCodeRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.AuthorizationCodeEntity;
import com.idp_core.idp_core.infrastructure.adapter.mapper.AuthorizationCodeMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorizationCodeRepositoryImpl implements AuthorizationCodeRepositoryPort {

    private final JpaAuthorizationCodeRepository jpaRepo;
    private final AuthorizationCodeMapper mapper;

    public AuthorizationCodeRepositoryImpl(
            JpaAuthorizationCodeRepository jpaRepo,
            AuthorizationCodeMapper mapper
    ) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<AuthorizationCode> findByCodeHash(String codeHash) {
        return jpaRepo.findByCodeHash(codeHash)
                .map(mapper::toDomain);
    }

    @Override
    public AuthorizationCode save(AuthorizationCode code) {
        AuthorizationCodeEntity entity = mapper.toEntity(code);  // convierte dominio -> entity
        AuthorizationCodeEntity saved = jpaRepo.save(entity);    // JPA trabaja con la entity
        return mapper.toDomain(saved);                           // entity -> dominio
    }

    @Override
    public void delete(AuthorizationCode code) {
        AuthorizationCodeEntity entity = mapper.toEntity(code);
        jpaRepo.delete(entity);
    }
}