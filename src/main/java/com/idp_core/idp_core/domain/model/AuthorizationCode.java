package com.idp_core.idp_core.domain.model;

import java.time.LocalDateTime;

public class AuthorizationCode {

    private String codeHash;
    private User user;
    private Client client;
    private String redirectUri;
    private LocalDateTime expiresAt;
    private LocalDateTime usedAt; //  nuevo campo

    public AuthorizationCode(String codeHash, User user, Client client, String redirectUri, LocalDateTime expiresAt) {
        this.codeHash = codeHash;
        this.user = user;
        this.client = client;
        this.redirectUri = redirectUri;
        this.expiresAt = expiresAt;
        this.usedAt = null; // inicialmente no usado
    }

    // Getters
    public String getCodeHash() { return codeHash; }
    public User getUser() { return user; }
    public Client getClient() { return client; }
    public String getRedirectUri() { return redirectUri; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public LocalDateTime getUsedAt() { return usedAt; } //  getter nuevo

    // Setters
    public void setCodeHash(String codeHash) { this.codeHash = codeHash; }
    public void setUser(User user) { this.user = user; }
    public void setClient(Client client) { this.client = client; }
    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; } //  setter nuevo
}