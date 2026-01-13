package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.User;

import java.util.Optional;


public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}