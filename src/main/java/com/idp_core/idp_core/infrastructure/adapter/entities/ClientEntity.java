package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "clients", schema = "config")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "client_secret_hash", nullable = false)
    private String clientSecretHash;

    private String name;
    private String type;

    @ElementCollection
    @CollectionTable(name = "client_redirect_uris",schema="auth",joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri")
    private Set<String> redirectUris;

    @ElementCollection
    @CollectionTable(name = "client_allowed_grant_types",schema="auth", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> allowedGrantTypes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClientEntity() {}

    public ClientEntity(String clientId, String clientSecretHash) {
        this.clientId = clientId;
        this.clientSecretHash = clientSecretHash;
    }

    public ClientEntity(Long id) {
        this.id = id;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientSecretHash() { return clientSecretHash; }
    public void setClientSecretHash(String clientSecretHash) { this.clientSecretHash = clientSecretHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Set<String> getRedirectUris() { return redirectUris; }
    public void setRedirectUris(Set<String> redirectUris) { this.redirectUris = redirectUris; }
    public Set<String> getAllowedGrantTypes() { return allowedGrantTypes; }
    public void setAllowedGrantTypes(Set<String> allowedGrantTypes) { this.allowedGrantTypes = allowedGrantTypes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
