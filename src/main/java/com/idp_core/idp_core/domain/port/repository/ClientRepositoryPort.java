package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.Client;

import java.util.Optional;

public interface ClientRepositoryPort  {
    Optional<Client> findByClientId(String clientId);
}

