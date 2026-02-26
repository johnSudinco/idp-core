package com.idp_core.idp_core.infrastructure.adapter.repository.adapter;

import com.idp_core.idp_core.domain.model.UserConsent;
import com.idp_core.idp_core.domain.port.repository.UserConsentRepositoryPort;
import com.idp_core.idp_core.infrastructure.adapter.entities.UserConsentEntity;
import com.idp_core.idp_core.infrastructure.adapter.repository.jpa.JpaUserConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsentRepositoryAdapter implements UserConsentRepositoryPort {

    private final JpaUserConsentRepository jpaRepository;

    @Override
    public UserConsent save(UserConsent domainModel) {
        UserConsentEntity entity = new UserConsentEntity();

        // Mapeo manual (o puedes usar MapStruct si lo tienes configurado)
        entity.setUserId(domainModel.getUserId());
        entity.setConsentType(domainModel.getConsentType());
        entity.setVersion(domainModel.getVersion());
        entity.setAction(domainModel.getAction());
        entity.setUserAgent(domainModel.getUserAgent());
        entity.setIpAddress(domainModel.getIpAddress());
        entity.setAccepted(domainModel.isAccepted());
        entity.setAcceptedAt(domainModel.getAcceptedAt());
        entity.setDocumentPath(domainModel.getDocumentPath());
        entity.setDocumentHash(domainModel.getDocumentHash());

        UserConsentEntity savedEntity = jpaRepository.save(entity);

        // Retornamos el modelo de dominio actualizado con su ID
        return UserConsent.builder()
                .id(savedEntity.getId())
                .userId(savedEntity.getUserId())
                .isAccepted(savedEntity.isAccepted())
                .build();
    }
}