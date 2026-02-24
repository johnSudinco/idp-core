package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.RoleRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.RegisterRequest;
import com.idp_core.idp_core.application.dto.RegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse execute(RegisterRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username requerido");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email requerido");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password requerido");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalStateException("Username ya existe");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                hashedPassword,
                request.isTwoFactor(),
                request.getName(),
                request.getLastname(),
                request.getIdentification(),
                request.getPhone(),
                request.getAddress()
        );

        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setTwoFactor(savedUser.isTwoFactor());
        response.setName(savedUser.getName());
        response.setLastname(savedUser.getLastname());
        response.setIdentification(savedUser.getIdentification());
        response.setPhone(savedUser.getPhone());
        response.setAddress(savedUser.getAddress());
        response.setMessage("Usuario registrado satisfactoriamente");

        return response;
    }

}

