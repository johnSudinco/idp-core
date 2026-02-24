package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.port.repository.TokenRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.RefreshTokenEntity;
import com.idp_core.idp_core.infrastructure.adapter.mapper.TokenMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepositoryPort {

    private final JpaRefreshTokenRepository jpaTokenRepository;
    private final TokenMapper tokenMapper;

    public TokenRepositoryImpl(JpaRefreshTokenRepository jpaTokenRepository, TokenMapper tokenMapper) {
        this.jpaTokenRepository = jpaTokenRepository;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public Optional<Token> findByValue(String value) {
        return jpaTokenRepository.findByToken(value)   //  usar "findByToken"
                .map(tokenMapper::toDomain);
    }

    @Override
    public Token save(Token token) {
        RefreshTokenEntity entity = tokenMapper.toEntity(token);
        RefreshTokenEntity saved = jpaTokenRepository.save(entity);
        return tokenMapper.toDomain(saved);
    }

    @Override
    public void delete(Token token) {
        RefreshTokenEntity entity = tokenMapper.toEntity(token);
        jpaTokenRepository.delete(entity);
    }
}
