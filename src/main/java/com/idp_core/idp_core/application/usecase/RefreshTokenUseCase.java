package com.idp_core.idp_core.application.usecase;
import com.idp_core.idp_core.application.port.TokenHashService;
import com.idp_core.idp_core.domain.model.RefreshTokenFactory;
import com.idp_core.idp_core.domain.model.RefreshToken;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.RefreshTokenRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.RefreshRequest;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenUseCase {

    private final JwtServicePort jwtService;
    private final UserRepositoryPort userRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final TokenHashService tokenHashService;

    public RefreshTokenUseCase(
            JwtServicePort jwtService,
            UserRepositoryPort userRepository,
            RefreshTokenRepositoryPort refreshTokenRepository,
            TokenHashService tokenHashService
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenHashService = tokenHashService;
    }

    public AuthResponse execute(RefreshRequest request) {

        String tokenHash = tokenHashService.hash(request.getRefreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));

        if (refreshToken.isRevoked() || refreshToken.isExpired()) {
            throw new IllegalArgumentException("Refresh token inválido");
        }

        Claims claims = jwtService.getClaims(request.getRefreshToken());
        Long userId = Long.parseLong(claims.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // ROTACIÓN DE REFRESH TOKEN
        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenRepository.save(
                RefreshTokenFactory.create(
                        user.getId(),
                        claims,
                        newRefreshToken,
                        tokenHashService
                )
        );

        return new AuthResponse(user.getId(), newAccessToken, newRefreshToken);
    }

    public void revokeByRefreshToken(String refreshToken) {
        String tokenHash = tokenHashService.hash(refreshToken);

        refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(token -> {
                    if (!token.isRevoked()) {
                        token.revoke();
                        refreshTokenRepository.save(token);
                    }
                });
    }
}

