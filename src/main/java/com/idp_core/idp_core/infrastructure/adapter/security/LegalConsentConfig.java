package com.idp_core.idp_core.infrastructure.adapter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.legal.consent")
public class LegalConsentConfig {
    private String type;
    private String version;
    private String documentPath;
    private String documentHash;
}