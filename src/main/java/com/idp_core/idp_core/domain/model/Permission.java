package com.idp_core.idp_core.domain.model;

import java.time.LocalDateTime;

public class Permission {

    private Long id;
    private String name;        // ej: user:create
    private String description;
    private LocalDateTime createdAt;

    // Constructor protegido para evitar instanciación directa sin validación
    protected Permission() {}
    public Permission(Long id, String name) { this.id = id; this.name = name; }
    public Permission(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Permission name requerido");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Permission description requerido");
        }
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // equals & hashCode por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
