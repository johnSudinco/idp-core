package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.exception.InvalidCredentialsException;
import com.idp_core.idp_core.domain.exception.InvalidTwoFactorCodeException;
import com.idp_core.idp_core.domain.exception.UserNotFoundException;
import com.idp_core.idp_core.domain.port.external.EmailServicePort;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.LoginRequest;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginUserUseCase {

    private final UserRepositoryPort userRepository;
    private final JwtServicePort jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailServicePort emailService;

    public LoginUserUseCase(
            UserRepositoryPort userRepository,
            JwtServicePort jwtService,
            PasswordEncoder passwordEncoder,
            EmailServicePort emailService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /* ======================
       LOGIN PRINCIPAL
       ====================== */
    public AuthResponse execute(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        // Flujo 2FA
        if (user.isTwoFactor()) {
            String code = generateTwoFactorCode();

            user.activateTwoFactor(code);
            userRepository.save(user);

            emailService.sendTwoFactorCode(user.getEmail(), code);

            return new AuthResponse(null, null, "2FA_REQUIRED");
        }

        // Login normal
        return generateTokens(user);
    }

    /* ======================
       VERIFICACIÓN 2FA
       ====================== */
    public AuthResponse verifyTwoFactor(String username, String code) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!user.verifyTwoFactorCode(code)) {
            throw new InvalidTwoFactorCodeException("Código inválido");
        }

        user.clearTwoFactorCode();
        userRepository.save(user);

        return generateTokens(user);
    }

    /* ======================
       HELPERS
       ====================== */
    private AuthResponse generateTokens(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }

    private String generateTwoFactorCode() {
        return String.format("%06d", new Random().nextInt(1_000_000));
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
