package com.idp_core.idp_core.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private boolean twoFactor;
    private String name;
    private String lastname;
    private String identification;
    private String phone;
    private String address;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
