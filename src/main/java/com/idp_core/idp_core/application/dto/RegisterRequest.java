package com.idp_core.idp_core.application.dto;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private boolean twoFactor;
    private String twoFactorCode;
    // Constructor vacío (necesario para serialización/deserialización en Spring)
    public RegisterRequest() {}

    // Constructor completo
    public RegisterRequest(String username, String email, String password,boolean twoFactor) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.twoFactor= twoFactor;
    }

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getTwoFactor() {
        return twoFactor;
    }

    public void setTwoFactor(boolean twoFactor) {
        this.twoFactor = twoFactor;
    }

    // toString (útil para logs y debugging)
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
