package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.CreateUserRequest;
import com.idp_core.idp_core.application.usecase.CreateUserUseCase;
import com.idp_core.idp_core.application.usecase.GetUserUseCase;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.application.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserUseCase getUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody CreateUserRequest request
    ) {
        User user = createUserUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WEBMASTER')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = getUserUseCase.execute(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('WEBMASTER')")
    public ResponseEntity<UserResponse> getUserByUsername(
            @RequestParam String username
    ) {
        User user = getUserUseCase.getUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }
}

