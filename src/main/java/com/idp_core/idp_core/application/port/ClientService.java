package com.idp_core.idp_core.application.port;

import com.idp_core.idp_core.domain.exception.InvalidClientException;
import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepositoryPort clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepositoryPort clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client validateClient(String clientId, String rawSecret) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new InvalidClientException("Cliente no registrado"));

        if (!passwordEncoder.matches(rawSecret, client.getClientSecretHash())) {
            throw new InvalidClientException("Credenciales del cliente inválidas");
        }

        return client;
    }
}

