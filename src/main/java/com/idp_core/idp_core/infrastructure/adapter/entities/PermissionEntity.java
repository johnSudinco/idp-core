package com.idp_core.idp_core.infrastructure.adapter.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions", schema = "auth")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public PermissionEntity() {}

    public PermissionEntity(String name) {
        this.name = name;
    }

    //  Constructor adicional
    public PermissionEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
