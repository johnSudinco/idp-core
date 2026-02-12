package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.*;
import com.idp_core.idp_core.application.port.SessionService;
import com.idp_core.idp_core.application.usecase.*;
import com.idp_core.idp_core.domain.port.external.EmailServicePort;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import com.idp_core.idp_core.web.common.ApiResponse;
import com.idp_core.idp_core.web.common.Auditable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Lombok genera el constructor con todos los final
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final PasswordRecoveryUseCase passwordRecoveryUseCase;
    private final JwtServicePort jwtService;
    private final SessionService sessionService ;

    @PostMapping("/register")
    @Auditable(action = "REGISTER", targetType = "USER")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = registerUserUseCase.execute(request);
        return ResponseEntity.ok(new ApiResponse<>(true, response, "Usuario registrado correctamente"));
    }

    @PostMapping("/login")
    @Auditable(action = "LOGIN_SUCCESS", targetType = "USER")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        AuthResponse response = loginUseCase.execute(request);
        return ResponseEntity.ok(new ApiResponse<>(true, response, "Login exitoso"));
    }

    @PostMapping("/refresh")
    @Auditable(action = "REFRESH", targetType = "USER")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody RefreshRequest request) {
            AuthResponse response = refreshTokenUseCase.execute(request);
            return ResponseEntity.ok(new ApiResponse<>(true, response, "Token refrescado correctamente"));
    }

    @GetMapping("/validate")
    @Auditable(action = "VALIDATE", targetType = "USER")
    public ResponseEntity<ApiResponse<Boolean>> validate(@RequestParam String token) {
            boolean isValid = loginUseCase.validateToken(token);
            return ResponseEntity.ok(new ApiResponse<>(true, isValid, isValid ? "Token válido" : "Token inválido"));
    }

    @PostMapping("/verify-2fa")
    @Auditable(action = "VERIFY_2FA", targetType = "USER")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyTwoFactor(@RequestBody VerifyTwoFactorRequest request) {
            AuthResponse response = loginUseCase.verifyTwoFactor(request.getUsername(), request.getCode());
            return ResponseEntity.ok(new ApiResponse<>(true, response, "2FA verificado correctamente"));
    }

    @PostMapping("/forgot-password")
    @Auditable(action = "FORGOT_PASSWORD", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
            passwordRecoveryUseCase.generateRecoveryToken(request.getEmail());
            log.info("Forgot password solicitado para email: {}", request.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(true, "OK", "Se envió un correo con instrucciones"));
    }

    @PostMapping("/reset-password")
    @Auditable(action = "RESET_PASSWORD", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
            passwordRecoveryUseCase.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, "OK", "Contraseña actualizada correctamente"));
    }
    @PostMapping("/logout")
    @Auditable(action = "LOGOUT", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> logout(
            HttpServletRequest request,
            @RequestBody LogoutRequest logoutRequest) {
        // 1️ Extraer token y usuario
        String accessToken = extractToken(request);
        Long userId = jwtService.getUserIdFromToken(accessToken);
        // 2️ Terminar sesión en DB
        sessionService.terminateSession(userId, accessToken);
        // 3️ Revocar refresh token
        refreshTokenUseCase.revokeByRefreshToken(logoutRequest.getRefreshToken());
        // 4 Responder al cliente
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
