package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "auth")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(name = "two_factor", nullable = false)
    private boolean twoFactor = false;

    @Column(name = "two_factor_code_hash")
    private String twoFactorCodeHash;

    @Column(name = "two_factor_expiration")
    private LocalDateTime twoFactorExpiration;

    @Column(name = "two_factor_attempts")
    private int twoFactorAttempts;

    @Column
    private String identification;

    @Column
    private String phone;

    @Column
    private String address;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleEntity> roles = new HashSet<>();

    public UserEntity() {}

    public UserEntity(Long id) {
        this.id = id;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isTwoFactor() { return twoFactor; }
    public void setTwoFactor(boolean twoFactor) { this.twoFactor = twoFactor; }

    public String getTwoFactorCodeHash() { return twoFactorCodeHash; }
    public void setTwoFactorCodeHash(String twoFactorCodeHash) { this.twoFactorCodeHash = twoFactorCodeHash; }

    public LocalDateTime getTwoFactorExpiration() { return twoFactorExpiration; }
    public void setTwoFactorExpiration(LocalDateTime twoFactorExpiration) { this.twoFactorExpiration = twoFactorExpiration; }

    public int getTwoFactorAttempts() { return twoFactorAttempts; }
    public void setTwoFactorAttempts(int twoFactorAttempts) { this.twoFactorAttempts = twoFactorAttempts; }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<UserRoleEntity> getRoles() { return roles; }
    public void setRoles(Set<UserRoleEntity> roles) { this.roles = roles; }
}
