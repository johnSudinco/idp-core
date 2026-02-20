package com.idp_core.idp_core.application.dto;


public record CreateUserRequest(
        String username,
        String email,
        String password,
        boolean twoFactor,
        String name,
        String lastname,
        String identification,
        String phone,
        String address
) {}
