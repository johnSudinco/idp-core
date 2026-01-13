package com.idp_core.idp_core.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    private String code;
    private String clientId;
    private String redirectUri;

}

