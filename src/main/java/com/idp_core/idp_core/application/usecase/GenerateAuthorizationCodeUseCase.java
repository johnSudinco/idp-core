package com.idp_core.idp_core.application.usecase;


import com.idp_core.idp_core.domain.model.AuthorizationCode;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.AuthorizationCodeRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;

import java.time.LocalDateTime;
import java.util.UUID;

public class GenerateAuthorizationCodeUseCase {

    private final AuthorizationCodeRepositoryPort authorizationCodeRepository;
    private final ClientRepositoryPort clientRepository;
    private final UserRepositoryPort userRepository;

    public GenerateAuthorizationCodeUseCase(AuthorizationCodeRepositoryPort authorizationCodeRepository,
                                            ClientRepositoryPort clientRepository,
                                            UserRepositoryPort userRepository) {
        this.authorizationCodeRepository = authorizationCodeRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public AuthorizationCode execute(Long userId, String clientIdentifier, String redirectUri) {
        // Generar un código único
        String rawCode = UUID.randomUUID().toString();
        String codeHash = hashCode(rawCode);

        // Buscar entidades
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Client client = clientRepository.findByClientId(clientIdentifier)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Crear entidad
        AuthorizationCode code = new AuthorizationCode(
                codeHash,
                user,
                client,
                redirectUri,
                LocalDateTime.now().plusMinutes(5)
        );

        // Guardar en repositorio
        return authorizationCodeRepository.save(code);
    }

    private String hashCode(String rawCode) {
        return Integer.toHexString(rawCode.hashCode());
    }
}

