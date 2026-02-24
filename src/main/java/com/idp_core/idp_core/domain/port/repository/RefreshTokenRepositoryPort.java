package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Token;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    Token save(Token token);              // devuelve el Token persistido con id asignado
    Optional<Token> findByToken(String token); // buscar por el campo "token"
    void delete(Token token);             // elimina el Token
}
