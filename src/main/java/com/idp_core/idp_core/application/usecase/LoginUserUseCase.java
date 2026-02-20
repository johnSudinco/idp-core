package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.LoginRequest;
import com.idp_core.idp_core.application.port.SessionService;
import com.idp_core.idp_core.domain.exception.InvalidCredentialsException;
import com.idp_core.idp_core.domain.exception.InvalidTwoFactorCodeException;
import com.idp_core.idp_core.domain.exception.UserNotFoundException;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.external.EmailServicePort;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import com.idp_core.idp_core.domain.port.repository.RolePermissionRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.List;

@Service
public class LoginUserUseCase {

    private static final Long DEFAULT_CLIENT_ID = 1L;

    private final UserRepositoryPort userRepository;
    private final JwtServicePort jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailServicePort emailService;
    private final SessionService sessionService;
    private final RolePermissionRepositoryPort rolePermissionRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    public LoginUserUseCase(
            UserRepositoryPort userRepository,
            JwtServicePort jwtService,
            PasswordEncoder passwordEncoder,
            EmailServicePort emailService,
            SessionService sessionService,
            RolePermissionRepositoryPort rolePermissionRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.sessionService = sessionService;
        this.rolePermissionRepository = rolePermissionRepository;
    }


    public AuthResponse execute(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        System.out.println("MATCHES: " +
                passwordEncoder.matches(request.getPassword(), user.getPasswordHash()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        // Flujo 2FA
        if (user.isTwoFactor()) {
            String code = generateTwoFactorCode();
            String hashedCode = passwordEncoder.encode(code);
            user.activateTwoFactor(hashedCode, LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);

            emailService.sendTwoFactorCode(user.getEmail(), code);

            return new AuthResponse(null, null, "2FA_REQUIRED");
        }
        return generateTokens(user);
    }

    public AuthResponse verifyTwoFactor(String username, String code) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        //  Validar expiración
        if (user.getTwoFactorExpiration() == null ||
                user.getTwoFactorExpiration().isBefore(LocalDateTime.now())) {
            throw new InvalidTwoFactorCodeException("Código expirado");
        }

        //  Validar código con hash
        if (!passwordEncoder.matches(code, user.getTwoFactorCodeHash())) {
            user.incrementTwoFactorAttempts();

            if (user.getTwoFactorAttempts() >= 5) {
                user.clearTwoFactorCode();
            }

            userRepository.save(user);
            throw new InvalidTwoFactorCodeException("Código inválido");
        }

        user.clearTwoFactorCode();
        userRepository.save(user);

        return generateTokens(user);
    }

    private AuthResponse generateTokens(User user) {

        // 1. Obtener permisos del usuario (vía roles)
        List<String> permissions = rolePermissionRepository.findPermissionNamesByUserId(user.getId());

        // 2. Generar tokens
        String accessToken = jwtService.generateToken(user, permissions);

        String refreshToken = jwtService.generateRefreshToken(user);

        // 3. Registrar sesión
        sessionService.registerSession(
                user.getId(),
                DEFAULT_CLIENT_ID,
                accessToken);

        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }

    private String generateTwoFactorCode() {
        int number = secureRandom.nextInt(1_000_000);
        return String.format("%06d", number);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}