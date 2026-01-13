package com.idp_core.idp_core.application.usecase;
import com.idp_core.idp_core.domain.model.RefreshToken;
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.repository.RefreshTokenRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.RefreshRequest;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenUseCase {

    private final JwtServicePort jwtService;
    private final UserRepositoryPort userRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;

    public RefreshTokenUseCase(JwtServicePort jwtService, UserRepositoryPort userRepository,RefreshTokenRepositoryPort refreshTokenRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResponse execute(RefreshRequest request) {
        // 1. Validar el refresh token en BD
        String tokenHash = DigestUtils.sha256Hex(request.getRefreshToken());
        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));

        if (refreshToken.isRevoked() || refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token inválido");
        }

        // 2. Validar claims del JWT
        Claims claims = jwtService.getClaims(request.getRefreshToken());
        Long userId = Long.parseLong(claims.getSubject());

        // 3. Buscar usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 4. Generar nuevo access token
        String newAccessToken = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), newAccessToken, request.getRefreshToken());
    }

    public void revokeByRefreshToken(String refreshTokenPlain) {
        String tokenHash = DigestUtils.sha256Hex(refreshTokenPlain);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token no encontrado"));

        refreshToken.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);
    }

}
