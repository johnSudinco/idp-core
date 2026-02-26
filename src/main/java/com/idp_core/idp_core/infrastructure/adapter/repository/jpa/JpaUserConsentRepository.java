package com.idp_core.idp_core.infrastructure.adapter.repository.jpa;


import com.idp_core.idp_core.infrastructure.adapter.entities.UserConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserConsentRepository extends JpaRepository<UserConsentEntity, Long> {
    // Aquí se podría añadir búsquedas por userId si fuera necesario
}