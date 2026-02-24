package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.application.dto.CreateClientCommand;
import com.idp_core.idp_core.domain.exception.ClientAlreadyExistsException;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.AuthClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.entities.ConfigClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.AuthClientRepository;
import com.idp_core.idp_core.infrastructure.adapter.repository.ConfigClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class CreateClientUseCase {

    private final ConfigClientRepository configRepository; // apunta a config.clients
    private final AuthClientRepository authRepository;     // apunta a auth.clients
    private final PasswordEncoder passwordEncoder;

    public CreateClientUseCase(ConfigClientRepository configRepository,
                               AuthClientRepository authRepository,
                               PasswordEncoder passwordEncoder) {
        this.configRepository = configRepository;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client execute(CreateClientCommand command) {

        // Validación: no permitir clientId duplicado en config
        if (configRepository.existsByClientId(command.clientId())) {
            throw new ClientAlreadyExistsException(
                    "Client ID already exists: " + command.clientId()
            );
        }

        // Hash seguro de clientSecret
        String hashedSecret = passwordEncoder.encode(command.clientSecret());

        //  Guardar en config.clients
        ConfigClientEntity configEntity = new ConfigClientEntity();
        configEntity.setClientId(command.clientId());
        configEntity.setClientSecretHash(hashedSecret);
        configEntity.setName(command.name());
        configEntity.setType(command.type());
        configEntity.setRedirectUris(command.redirectUris());
        configEntity.setAllowedGrantTypes(command.allowedGrantTypes());
        configEntity.setCreatedAt(LocalDateTime.now());
        configEntity.setUpdatedAt(LocalDateTime.now());

        ConfigClientEntity savedConfig = configRepository.save(configEntity);

        // Replicar en auth.clients
        AuthClientEntity authEntity = new AuthClientEntity();
        authEntity.setClientId(savedConfig.getClientId());
        authEntity.setClientSecretHash(savedConfig.getClientSecretHash());
        authEntity.setName(savedConfig.getName());
        authEntity.setType(savedConfig.getType());
        authEntity.setCreatedAt(savedConfig.getCreatedAt());
        authEntity.setUpdatedAt(savedConfig.getUpdatedAt());

        authRepository.save(authEntity);

        // Devolver el dominio
        return new Client(
                savedConfig.getId(),
                savedConfig.getClientId(),
                savedConfig.getClientSecretHash(),
                savedConfig.getName(),
                savedConfig.getType(),
                savedConfig.getRedirectUris(),
                savedConfig.getAllowedGrantTypes(),
                savedConfig.getCreatedAt(),
                savedConfig.getUpdatedAt(),
                Collections.emptyList()
        );
    }
}
