package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GetUserUseCase {
    private final UserRepositoryPort repository;

    public GetUserUseCase(UserRepositoryPort repository) {
        this.repository = repository;
    }
    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
    public Optional<User> execute(Long id_users) {
        return repository.findById(id_users);
    }
}
