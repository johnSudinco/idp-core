package com.idp_core.idp_core.domain.model;

import java.time.LocalDateTime;

public class UserRole {

    private final User user;
    private final Role role;
    private final LocalDateTime assignedAt;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.assignedAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public String getRoleName() {
        return role.getName();
    }
}