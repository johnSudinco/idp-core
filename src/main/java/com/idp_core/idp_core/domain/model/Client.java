package com.idp_core.idp_core.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Client {

    private Long id;
    private String clientId;
    private String clientSecretHash;
    private String name;
    private String type;
    private Set<String> redirectUris;
    private Set<String> allowedGrantTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> refreshTokenIds;
    public Client(Long id) { this.id = id; }
    // Constructor completo con id
    public Client(Long id, String clientId, String clientSecretHash, String name, String type,
                  Set<String> redirectUris, Set<String> allowedGrantTypes,
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                  List<Long> refreshTokenIds) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecretHash = clientSecretHash;
        this.name = name;
        this.type = type;
        this.redirectUris = redirectUris;
        this.allowedGrantTypes = allowedGrantTypes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.refreshTokenIds = refreshTokenIds;
    }

    // Constructor alternativo sin id (para crear nuevos clientes)
    public Client(String clientId, String clientSecretHash, String name, String type,
                  Set<String> redirectUris, Set<String> allowedGrantTypes,
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                  List<Long> refreshTokenIds) {
        this(null, clientId, clientSecretHash, name, type,
                redirectUris, allowedGrantTypes, createdAt, updatedAt, refreshTokenIds);
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClientId() { return clientId; }
    public String getClientSecretHash() { return clientSecretHash; }
    public String getName() { return name; }
    public String getType() { return type; }
    public Set<String> getRedirectUris() { return redirectUris; }
    public Set<String> getAllowedGrantTypes() { return allowedGrantTypes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Long> getRefreshTokenIds() { return refreshTokenIds; }
}
