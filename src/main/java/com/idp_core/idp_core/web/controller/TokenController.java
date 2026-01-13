package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.TokenRequest;
import com.idp_core.idp_core.application.dto.TokenResponse;
import com.idp_core.idp_core.application.usecase.ExchangeAuthorizationCodeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final ExchangeAuthorizationCodeUseCase exchangeAuthorizationCodeUseCase;

    public TokenController(ExchangeAuthorizationCodeUseCase exchangeAuthorizationCodeUseCase) {
        this.exchangeAuthorizationCodeUseCase = exchangeAuthorizationCodeUseCase;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> exchangeCode(@RequestBody TokenRequest request) {
        String accessToken = exchangeAuthorizationCodeUseCase.execute(
                request.getCode(),
                request.getClientId(),
                request.getRedirectUri()
        );

        return ResponseEntity.ok(new TokenResponse(accessToken, "bearer", 3600));
    }
}
