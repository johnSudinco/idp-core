package com.idp_core.idp_core.application.dto;

import java.util.Set;

public record CreateClientRequest(
        String clientId,
        String clientSecret,
        String name,
        String type,
        Set<String> redirectUris,
        Set<String> allowedGrantTypes
) {}
