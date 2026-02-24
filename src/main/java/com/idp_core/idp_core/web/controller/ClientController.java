package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.ClientResponse;
import com.idp_core.idp_core.application.dto.CreateClientCommand;
import com.idp_core.idp_core.application.dto.CreateClientRequest;
import com.idp_core.idp_core.application.usecase.CreateClientUseCase;
import com.idp_core.idp_core.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientUseCase createClientUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponse>> createClient(@RequestBody CreateClientRequest request) {

        CreateClientCommand command = new CreateClientCommand(
                request.clientId(),
                request.clientSecret(),
                request.name(),
                request.type(),
                request.redirectUris(),
                request.allowedGrantTypes()
        );

        var client = createClientUseCase.execute(command);

        return ResponseEntity.ok(
                new ApiResponse<>(true, ClientResponse.fromDomain(client), "Cliente creado exitosamente")
        );
    }
}
