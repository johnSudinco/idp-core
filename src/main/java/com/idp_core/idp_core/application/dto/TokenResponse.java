package com.idp_core.idp_core.application.dto;

public class TokenResponse {
    private String accessToken;
    private String tokenType;
    private int expiresIn;

    public TokenResponse(String accessToken, String tokenType, int expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }


}
