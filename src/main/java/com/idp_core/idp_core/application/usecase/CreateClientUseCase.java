package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.application.dto.CreateClientCommand;
import com.idp_core.idp_core.domain.exception.ClientAlreadyExistsException;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class CreateClientUseCase {

    private final ClientRepositoryPort repository;
    private final PasswordEncoder passwordEncoder; // Para hashear el clientSecret de manera segura

    public CreateClientUseCase(ClientRepositoryPort repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client execute(CreateClientCommand command) {

        // Validación: no permitir clientId duplicado
        if (repository.existsByClientId(command.clientId())) {
            throw new ClientAlreadyExistsException(
                    "Client ID already exists: " + command.clientId()
            );
        }

        // Hash seguro de clientSecret
        String hashedSecret = passwordEncoder.encode(command.clientSecret());

        // Crear el dominio Client
        Client client = new Client(
                command.clientId(),                  // clientId
                hashedSecret,                        // clientSecretHash
                command.name(),                      // name
                command.type(),                      // type
                command.redirectUris() != null ? command.redirectUris() : Collections.emptySet(),  // redirectUris
                command.allowedGrantTypes() != null ? command.allowedGrantTypes() : Collections.emptySet(), // allowedGrantTypes
                LocalDateTime.now(),                 // createdAt
                LocalDateTime.now(),                 // updatedAt
                Collections.emptyList()              // refreshTokenIds inicial vacía
        );

        // Guardar usando el puerto
        return repository.save(client);
    }
}