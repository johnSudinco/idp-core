package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Token;

import java.util.Optional;

public interface TokenRepositoryPort {
    Optional<Token> findByValue(String value);
    Token save(Token token);
    void delete(Token token);
}
