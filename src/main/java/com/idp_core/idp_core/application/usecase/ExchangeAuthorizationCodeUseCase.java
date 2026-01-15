package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.AuthorizationCode;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import com.idp_core.idp_core.domain.port.repository.AuthorizationCodeRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ExchangeAuthorizationCodeUseCase {

    private final AuthorizationCodeRepositoryPort authorizationCodeRepository;
    private final ClientRepositoryPort clientRepository;
    private final JwtServicePort tokenService; // servicio para generar JWT

    public ExchangeAuthorizationCodeUseCase(AuthorizationCodeRepositoryPort authorizationCodeRepository,
                                            ClientRepositoryPort clientRepository,
                                            JwtServicePort tokenService) {
        this.authorizationCodeRepository = authorizationCodeRepository;
        this.clientRepository = clientRepository;
        this.tokenService = tokenService;
    }

    public String execute(String codeHash, String clientIdentifier, String redirectUri) {
        // Buscar el código
        AuthorizationCode code = authorizationCodeRepository.findByCodeHash(codeHash)
                .orElseThrow(() -> new IllegalArgumentException("Authorization code not found"));

        // Validar expiración
        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Authorization code expired");
        }

        // Validar que no haya sido usado
        if (code.getUsedAt() != null) {
            throw new IllegalArgumentException("Authorization code already used");
        }

        // Validar cliente
        Client client = clientRepository.findByClientId(clientIdentifier)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        if (!code.getClient().getId().equals(client.getId())) {
            throw new IllegalArgumentException("Authorization code does not belong to this client");
        }

        // Validar redirectUri
        if (!code.getRedirectUri().equals(redirectUri)) {
            throw new IllegalArgumentException("Redirect URI mismatch");
        }

        // Marcar el código como usado
        code.setUsedAt(LocalDateTime.now());
        authorizationCodeRepository.save(code);

        // Generar Access Token (ejemplo JWT)
        return tokenService.generateRefreshToken(code.getUser());
    }
}
