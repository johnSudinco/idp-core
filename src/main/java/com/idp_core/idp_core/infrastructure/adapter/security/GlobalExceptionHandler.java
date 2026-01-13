package com.idp_core.idp_core.infrastructure.adapter.security;

import com.idp_core.idp_core.domain.exception.InvalidCredentialsException;
import com.idp_core.idp_core.domain.exception.InvalidTwoFactorCodeException;
import com.idp_core.idp_core.domain.exception.UserNotFoundException;
import com.idp_core.idp_core.web.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(InvalidTwoFactorCodeException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalid2FA(InvalidTwoFactorCodeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(RuntimeException ex) {
        log.error("Error no controlado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Error interno del servidor"));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                false, "ACCESS_DENIED",
                "No tienes permisos para realizar esta acción"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}

