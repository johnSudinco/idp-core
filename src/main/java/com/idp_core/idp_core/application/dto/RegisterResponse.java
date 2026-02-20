package com.idp_core.idp_core.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {

    private Long userId;
    private String username;
    private String email;
    private String name;
    private String lastname;
    private String identification;
    private String phone;
    private String address;
    private boolean twoFactor;
    private String message;

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


