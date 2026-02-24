package com.idp_core.idp_core.infrastructure.adapter.repository;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.port.repository.ClientRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.ClientEntity;
import com.idp_core.idp_core.infrastructure.adapter.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepositoryPort {

    private final SpringDataClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public Client save(Client client) {
        ClientEntity entity = mapper.toEntity(client);
        ClientEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Client> findByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByClientId(String clientId) {
        return repository.existsByClientId(clientId);
    }

    @Override
    public Client getReferenceById(Long id) {
        ClientEntity entity = repository.getReferenceById(id);
        return mapper.toDomain(entity);
    }
}