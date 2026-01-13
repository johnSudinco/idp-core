package com.idp_core.idp_core.application.usecase;


import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserRepositoryPort repository;

    public CreateUserUseCase(UserRepositoryPort repository) {
        this.repository = repository;
    }

    public User execute(User user) {
        return repository.save(user);
    }
}
