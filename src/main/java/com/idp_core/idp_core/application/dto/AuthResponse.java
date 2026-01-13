package com.idp_core.idp_core.application.dto;


public class AuthResponse {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String status;
    private String correlationId;
    public AuthResponse() {}

    // Constructor para login exitoso
    public AuthResponse(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = "SUCCESS";
    }

    // Constructor completo
    public AuthResponse(Long userId, String accessToken, String refreshToken, String status, String correlationId) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = status;
        this.correlationId = correlationId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    @Override
    public String toString() {
        return "AuthResponse{" +
                "userId=" + userId +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
