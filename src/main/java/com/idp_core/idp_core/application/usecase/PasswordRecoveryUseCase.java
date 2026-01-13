package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.PasswordResetToken;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.external.EmailServicePort;
import com.idp_core.idp_core.domain.port.repository.PasswordResetTokenRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordRecoveryUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordResetTokenRepositoryPort tokenRepository;
    private final EmailServicePort emailServicePort;
    private final PasswordEncoder passwordEncoder;

    public PasswordRecoveryUseCase(
            UserRepositoryPort userRepository,
            PasswordResetTokenRepositoryPort tokenRepository,
            EmailServicePort emailServicePort,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailServicePort = emailServicePort;
        this.passwordEncoder = passwordEncoder;
    }


    // GENERAR TOKEN
    public String generateRecoveryToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(
                user.getId(),
                token,
                LocalDateTime.now().plusMinutes(15)
        );

        tokenRepository.save(resetToken);

        String resetLink = "https://idp-core.com/auth/reset-password?token=" + token;
        emailServicePort.sendPasswordResetEmail(email, resetLink);

        return token;
    }

    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        if (resetToken.getUsedAt() != null) {
            throw new RuntimeException("Token ya utilizado");
        }

        User user = userRepository.findById(resetToken.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String encodedPassword = passwordEncoder.encode(newPassword);

        //  DOMINIO
        user.changePassword(encodedPassword);

        userRepository.save(user);

        resetToken.setUsedAt(LocalDateTime.now());
        tokenRepository.save(resetToken);
    }
}


