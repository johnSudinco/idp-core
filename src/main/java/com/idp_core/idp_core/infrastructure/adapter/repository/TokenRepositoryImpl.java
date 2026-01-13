package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.port.repository.TokenRepositoryPort;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepositoryPort {
    private final JpaTokenRepository jpaTokenRepository;

    public TokenRepositoryImpl(JpaTokenRepository jpaTokenRepository) {
        this.jpaTokenRepository = jpaTokenRepository;
    }

    @Override
    public Optional<Token> findByValue(String value) {
        return jpaTokenRepository.findByTokenHash(value);
    }

    @Override
    public Token save(Token token) {
        return jpaTokenRepository.save(token);
    }

    @Override
    public void delete(Token token) {
        jpaTokenRepository.delete(token);
    }
}
