package com.idp_core.idp_core.application.usecase;


import com.idp_core.idp_core.application.dto.CreateUserRequest;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(
            UserRepositoryPort repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(CreateUserRequest request) {

        repository.findByUsername(request.username())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Username ya existe");
                });

        repository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email ya existe");
                });

        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                "ACTIVE",
                false
        );

        return repository.save(user);
    }
}
