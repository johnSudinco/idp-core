package com.idp_core.idp_core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String identification;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(name = "two_factor")
    private boolean twoFactor;

    @Column(name = "two_factor_code_hash")
    private String twoFactorCodeHash;

    @Column(name = "two_factor_expiration")
    private LocalDateTime twoFactorExpiration;

    @Column(name = "two_factor_attempts")
    private int twoFactorAttempts;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRole> roles = new HashSet<>();

    protected User() {
        // Requerido por JPA
    }

    private User(
            String username,
            String email,
            String passwordHash,
            String status,
            boolean twoFactor,
            String name,
            String lastname,
            String identification,
            String phone,
            String address
    ) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.twoFactor = twoFactor;
        this.name = name;
        this.lastname = lastname;
        this.identification = identification;
        this.phone = phone;
        this.address = address;
    }

    public static User create(
            String username,
            String email,
            String passwordHash,
            boolean isTwoFactor,
            String name,
            String lastname,
            String identification,
            String phone,
            String address
    ) {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username requerido");

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email requerido");

        if (passwordHash == null || passwordHash.isBlank())
            throw new IllegalArgumentException("Password requerido");

        return new User(
                username,
                email,
                passwordHash,
                "ACTIVE",
                isTwoFactor,
                name,
                lastname,
                identification,
                phone,
                address
        );
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getPasswordHash() { return passwordHash; }

    public String getStatus() { return status; }

    public boolean isTwoFactor() { return twoFactor; }

    public String getTwoFactorCodeHash() { return twoFactorCodeHash; }

    public LocalDateTime getTwoFactorExpiration() { return twoFactorExpiration; }

    public int getTwoFactorAttempts() { return twoFactorAttempts; }

    public String getName() { return name; }

    public String getLastname() { return lastname; }

    public String getIdentification() { return identification; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public Set<UserRole> getRoles() { return roles; }

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



    public void activateTwoFactor(String hashedCode, LocalDateTime expiration) {
        this.twoFactorCodeHash = hashedCode;
        this.twoFactorExpiration = expiration;
        this.twoFactorAttempts = 0;
    }

    public boolean isTwoFactorExpired() {
        return twoFactorExpiration == null ||
                twoFactorExpiration.isBefore(LocalDateTime.now());
    }

    public void incrementTwoFactorAttempts() {
        this.twoFactorAttempts++;
    }

    public void clearTwoFactorCode() {
        this.twoFactorCodeHash = null;
        this.twoFactorExpiration = null;
        this.twoFactorAttempts = 0;
    }


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

