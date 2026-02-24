package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryPort {
    Client save(Client client);
    Optional<Client> findByClientId(String clientId);
    boolean existsByClientId(String clientId);

    Client getReferenceById(Long id);
}