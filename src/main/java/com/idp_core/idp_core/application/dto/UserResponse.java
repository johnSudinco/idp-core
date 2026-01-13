package com.idp_core.idp_core.application.dto;

import com.idp_core.idp_core.domain.model.User;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String status,
        Set<String> roles
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                anonymizeEmail(user.getEmail()),
                user.getStatus(),
                user.getRoleNames()
        );
    }

    private static String anonymizeEmail(String email) {
        if (email == null || !email.contains("@")) return "anon@test.com";
        String domain = email.substring(email.indexOf("@"));
        return "anon_" + email.hashCode() + domain;
    }
}


