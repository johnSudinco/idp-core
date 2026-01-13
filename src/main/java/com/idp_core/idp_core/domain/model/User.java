package com.idp_core.idp_core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", schema = "auth")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean twoFactor;

    @Column
    private String twoFactorCode;

    // Relación con UserRole (tabla puente)
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRole> roles = new HashSet<>();

    /* ======================
       CONSTRUCTORES
       ====================== */

    protected User() {}

    public User(String username, String email, String passwordHash, String status, boolean twoFactor) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.twoFactor = twoFactor;
    }

    /* ======================
       GETTERS
       ====================== */

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getPasswordHash() { return passwordHash; }

    public String getStatus() { return status; }

    public boolean isTwoFactor() { return twoFactor; }

    public String getTwoFactorCode() { return twoFactorCode; }

    public Set<UserRole> getRoles() { return roles; }

    /* ======================
       LÓGICA DE DOMINIO
       ====================== */

    public void addRole(Role role) {
        roles.add(new UserRole(this, role));
    }

    public void removeRole(Role role) {
        roles.removeIf(ur -> ur.getRole().equals(role));
    }

    public Set<String> getRoleNames() {
        return roles.stream()
                .map(UserRole::getRoleName)
                .collect(Collectors.toSet());
    }

    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new IllegalArgumentException("Password inválido");
        }
        this.passwordHash = newPasswordHash;
    }

    public void activateTwoFactor(String code) {
        this.twoFactor = true;
        this.twoFactorCode = code;
    }

    public void clearTwoFactorCode() {
        this.twoFactorCode = null;
    }

    public boolean verifyTwoFactorCode(String code) {
        return this.twoFactorCode != null && this.twoFactorCode.equals(code);
    }

    /* ======================
       EQUALS & HASHCODE
       ====================== */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
