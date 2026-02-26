package com.idp_core.idp_core.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    // --- NUEVOS CAMPOS PARA EL CONSENTIMIENTO ---
    private ConsentInfo consent;

    // Campos de auditoría que llenaremos en el Controller
    private String ipAddress;
    private String userAgent;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsentInfo {
        private String consentType; // Ej: "DATA_PRIVACY"
        private String version;     // Ej: "v1.0"
        private boolean accepted;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
