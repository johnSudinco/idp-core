package com.idp_core.idp_core.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authorization_codes", schema = "auth")
public class AuthorizationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_hash", nullable = false)
    private String codeHash;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // relación con auth.users

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // relación con auth.clients.id (FK bigint)

    @Column(name = "redirect_uri", nullable = false)
    private String redirectUri;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    public AuthorizationCode() {}

    public AuthorizationCode(String codeHash, User user, Client client,
                             String redirectUri, LocalDateTime expiresAt) {
        this.codeHash = codeHash;
        this.user = user;
        this.client = client;
        this.redirectUri = redirectUri;
        this.expiresAt = expiresAt;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getCodeHash() { return codeHash; }
    public void setCodeHash(String codeHash) { this.codeHash = codeHash; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getRedirectUri() { return redirectUri; }
    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public LocalDateTime getUsedAt() { return usedAt; }
    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }
}
