package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.AuthorizationCode;
import com.idp_core.idp_core.domain.port.repository.AuthorizationCodeRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorizationCodeRepositoryImpl implements AuthorizationCodeRepositoryPort {
    private final JpaAuthorizationCodeRepository jpaRepo;

    public AuthorizationCodeRepositoryImpl(JpaAuthorizationCodeRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Optional<AuthorizationCode> findByCodeHash(String codeHash) {
        return jpaRepo.findByCodeHash(codeHash);
    }

    @Override
    public AuthorizationCode save(AuthorizationCode code) {
        return jpaRepo.save(code);
    }

    @Override
    public void delete(AuthorizationCode code) {
        jpaRepo.delete(code);
    }
}
