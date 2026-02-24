package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.application.port.TokenHashService;
import com.idp_core.idp_core.domain.model.Token;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.model.RefreshTokenFactory;
import com.idp_core.idp_core.domain.port.repository.RefreshTokenRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.RefreshRequest;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class TokenUseCase {

    private final JwtServicePort jwtService;
    private final UserRepositoryPort userRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;

    public TokenUseCase(
            JwtServicePort jwtService,
            UserRepositoryPort userRepository,
            RefreshTokenRepositoryPort refreshTokenRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResponse execute(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        Token token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));

        if (token.isRevoked() || token.isExpired()) {
            throw new IllegalArgumentException("Refresh token inválido");
        }

        Claims claims = jwtService.getClaims(refreshToken);
        Long userId = Long.parseLong(claims.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // ROTACIÓN DE REFRESH TOKEN
        token.revoke();
        refreshTokenRepository.save(token);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenRepository.save(
                RefreshTokenFactory.create(
                        user.getId(),
                        claims,
                        newRefreshToken
                )
        );

        return new AuthResponse(user.getId(), newAccessToken, newRefreshToken);
    }

    public void revokeByRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(token -> {
                    if (!token.isRevoked()) {
                        token.revoke();
                        refreshTokenRepository.save(token);
                    }
                });
    }
}
