package com.idp_core.idp_core.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles", schema = "auth")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    protected Role() {}

    public Role(String name) {
        this.name = name;
    }

    /* ======================
       GETTERS
       ====================== */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /* ======================
       EQUALS & HASHCODE
       ====================== */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
