package com.idp_core.idp_core.application.dto;

public class VerifyTwoFactorRequest {
    private String username;
    private String code;

    public VerifyTwoFactorRequest() {}

    public VerifyTwoFactorRequest(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
