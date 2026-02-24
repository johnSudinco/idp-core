package com.idp_core.idp_core.infrastructure.adapter.security;

import com.idp_core.idp_core.application.usecase.LogErrorEventUseCase;
import com.idp_core.idp_core.application.usecase.LogSecurityEventUseCase;
import com.idp_core.idp_core.domain.exception.*;
import com.idp_core.idp_core.domain.model.ErrorLog;
import com.idp_core.idp_core.domain.model.SecurityEvent;
import com.idp_core.idp_core.web.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LogErrorEventUseCase logErrorEventUseCase;
    private final LogSecurityEventUseCase logSecurityEventUseCase;
    private final HttpServletRequest request;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        // Registrar en security_events
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("USER_NOT_FOUND")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", ex.getMessage()))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        // Registrar en security_events
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("LOGIN_FAIL")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", ex.getMessage()))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(InvalidTwoFactorCodeException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalid2FA(InvalidTwoFactorCodeException ex) {
        // Registrar en security_events
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("MFA_FAIL")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", ex.getMessage()))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalState(
            IllegalStateException ex) {
        log.error("Tipo de excepción: {}", ex.getClass().getName(), ex);
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("BUSINESS_CONFLICT")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", ex.getMessage()))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(RuntimeException ex) {
        log.error("Error no controlado", ex);

        // Registrar en error_logs
        logErrorEventUseCase.execute(ErrorLog.builder()
                .level("ERROR")
                .service("idp_core")
                .message("Error no controlado")
                .exception(ex.toString())
                .context(Map.of(
                        "path", request.getRequestURI(),
                        "method", request.getMethod()
                ))
                .correlationId(request.getHeader("X-Correlation-Id"))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, null, "Error interno del servidor"));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        // Registrar en security_events
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("ACCESS_DENIED")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", "No tienes permisos"))
                .createdAt(LocalDateTime.now())
                .build());

        ApiResponse<String> response = new ApiResponse<>(
                false, "ACCESS_DENIED",
                "No tienes permisos para realizar esta acción"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleClientAlreadyExists(ClientAlreadyExistsException ex) {

        // Registrar en security_events si quieres auditar
        logSecurityEventUseCase.execute(SecurityEvent.builder()
                .eventType("CLIENT_ALREADY_EXISTS")
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .details(Map.of("reason", ex.getMessage()))
                .createdAt(LocalDateTime.now())
                .build());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

}
