package com.idp_core.idp_core.domain.port.repository;

import com.idp_core.idp_core.domain.model.UserConsent;

public interface UserConsentRepositoryPort {
    UserConsent save(UserConsent consent);
    // Podrías añadir: boolean hasAcceptedLatestVersion(Long userId, String type);
}