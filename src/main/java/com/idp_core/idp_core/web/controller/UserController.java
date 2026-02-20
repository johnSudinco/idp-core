package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.CreateUserRequest;
import com.idp_core.idp_core.application.usecase.GetUserUseCase;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.application.dto.UserResponse;
import com.idp_core.idp_core.web.common.Auditable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetUserUseCase getUserUseCase;

    public UserController(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping("/{id}")
    @Auditable(action = "GET_USERID", targetType = "USER")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = getUserUseCase.execute(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ')")
    @Auditable(action = "GET_USERNAME", targetType = "USER")
    public ResponseEntity<UserResponse> getUserByUsername(
            @RequestParam String username
    ) {
        User user = getUserUseCase.getUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }
}
