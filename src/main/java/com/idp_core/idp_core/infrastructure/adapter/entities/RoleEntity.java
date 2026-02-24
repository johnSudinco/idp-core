package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles", schema = "auth")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public RoleEntity() {}

    public RoleEntity(String name) {
        this.name = name;
    }

    //  Constructor adicional
    public RoleEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

