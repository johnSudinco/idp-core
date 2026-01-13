package com.idp_core.idp_core.application.dto;

public class LoginRequest {
    private String username;
    private String password;

    // Constructor vacío (necesario para deserialización JSON en Spring)
    public LoginRequest() {
    }

    // Constructor completo
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString (útil para logs y debugging)
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
