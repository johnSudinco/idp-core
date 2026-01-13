package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.usecase.CreateUserUseCase;
import com.idp_core.idp_core.application.usecase.GetUserUseCase;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.application.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User saved = createUserUseCase.execute(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = getUserUseCase.execute(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
        User user = getUserUseCase.getUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(UserResponse.from(user));
    }
}
