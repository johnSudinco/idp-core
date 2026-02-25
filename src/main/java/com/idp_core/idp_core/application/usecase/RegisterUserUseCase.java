package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.Role;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.RoleRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.RegisterRequest;
import com.idp_core.idp_core.application.dto.RegisterResponse;
import com.idp_core.idp_core.web.common.DataEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final DataEncryptor dataEncryptor;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(
            UserRepositoryPort userRepository,
            DataEncryptor dataEncryptor,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.dataEncryptor = dataEncryptor;
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

        String encryptedName = dataEncryptor.encrypt(request.getName());
        String encryptedLastName = dataEncryptor.encrypt(request.getLastname());
        String encryptedIdentification = dataEncryptor.encrypt(request.getIdentification());
        String encryptedPhone = dataEncryptor.encrypt(request.getPhone());
        String encryptedAddress = dataEncryptor.encrypt(request.getAddress());

        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                hashedPassword,
                request.isTwoFactor(),
                encryptedName,
                encryptedLastName,
                encryptedIdentification,
                encryptedPhone,
                encryptedAddress
        );


        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setTwoFactor(savedUser.isTwoFactor());
        response.setName(dataEncryptor.decrypt(savedUser.getName()));
        response.setLastname(dataEncryptor.decrypt(savedUser.getLastname()));
        response.setIdentification(dataEncryptor.decrypt(savedUser.getIdentification()));
        response.setPhone(dataEncryptor.decrypt(savedUser.getPhone()));
        response.setAddress(dataEncryptor.decrypt(savedUser.getAddress()));
        response.setMessage("Usuario registrado satisfactoriamente");

        return response;
    }

}

