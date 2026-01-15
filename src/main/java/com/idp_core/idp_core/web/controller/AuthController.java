package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.*;
import com.idp_core.idp_core.application.port.SessionService;
import com.idp_core.idp_core.application.usecase.*;
import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.domain.port.external.EmailServicePort;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import com.idp_core.idp_core.web.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Lombok genera el constructor con todos los final
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final PasswordRecoveryUseCase passwordRecoveryUseCase;
    private final EmailServicePort emailServicePort;
    private final LogAuditEventUseCase logAuditEventUseCase;
    private final JwtServicePort jwtService;
    private final SessionService sessionService ;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {

            RegisterResponse response = registerUserUseCase.execute(request);

            logAuditEventUseCase.execute(AuditLog.builder()
                    .actorUserId(response.getUserId())
                    .action("REGISTER")
                    .targetType("USER")
                    .targetId(response.getUserId())
                    .metadata("Usuario registrado")
                    .createdAt(LocalDateTime.now())
                    .build());

            return ResponseEntity.ok(new ApiResponse<>(true, response, "Usuario registrado correctamente"));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

            AuthResponse response = loginUseCase.execute(request);

            logAuditEventUseCase.execute(AuditLog.builder()
                    .actorUserId(response.getUserId())
                    .action("LOGIN_SUCCESS")
                    .targetType("USER")
                    .targetId(response.getUserId())
                    .correlationId(response.getCorrelationId())
                    .ipAddress(httpRequest.getRemoteAddr())
                    .userAgent(httpRequest.getHeader("User-Agent"))
                    .metadata("Login exitoso")
                    .createdAt(LocalDateTime.now())
                    .build());

            return ResponseEntity.ok(new ApiResponse<>(true, response, "Login exitoso"));

    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody RefreshRequest request) {

            AuthResponse response = refreshTokenUseCase.execute(request);
            return ResponseEntity.ok(new ApiResponse<>(true, response, "Token refrescado correctamente"));

    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validate(@RequestParam String token) {

            boolean isValid = loginUseCase.validateToken(token);
            return ResponseEntity.ok(new ApiResponse<>(true, isValid, isValid ? "Token válido" : "Token inválido"));

    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyTwoFactor(@RequestBody VerifyTwoFactorRequest request) {

            AuthResponse response = loginUseCase.verifyTwoFactor(request.getUsername(), request.getCode());
            return ResponseEntity.ok(new ApiResponse<>(true, response, "2FA verificado correctamente"));

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {

            passwordRecoveryUseCase.generateRecoveryToken(request.getEmail());
            log.info("Forgot password solicitado para email: {}", request.getEmail());

            logAuditEventUseCase.execute(AuditLog.builder()
                    .action("FORGOT_PASSWORD")
                    .targetType("USER")
                    .metadata("Solicitud de recuperación de contraseña")
                    .createdAt(LocalDateTime.now())
                    .build());

            return ResponseEntity.ok(new ApiResponse<>(true, "OK", "Se envió un correo con instrucciones"));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {

            passwordRecoveryUseCase.resetPassword(request.getToken(), request.getNewPassword());

            logAuditEventUseCase.execute(AuditLog.builder()
                    .action("RESET_PASSWORD")
                    .targetType("USER")
                    .correlationId(request.getToken())
                    .metadata("Contraseña actualizada")
                    .createdAt(LocalDateTime.now())
                    .build());

            return ResponseEntity.ok(new ApiResponse<>(true, "OK", "Contraseña actualizada correctamente"));

    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            HttpServletRequest request,
            @RequestBody LogoutRequest logoutRequest) {

        String accessToken = extractToken(request);

        if (!jwtService.validateToken(accessToken)) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, null, "Token inválido"));
        }

        Long userId = jwtService.getUserIdFromToken(accessToken);
        sessionService.terminateSession(userId, accessToken);

        refreshTokenUseCase.revokeByRefreshToken(logoutRequest.getRefreshToken());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "OK", "Sesión cerrada correctamente")
        );
    }



    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Token no encontrado en el header Authorization");
    }


}
