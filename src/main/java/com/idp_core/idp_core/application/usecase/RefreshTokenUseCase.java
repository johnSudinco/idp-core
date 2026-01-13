package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.model.User;
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

    public RefreshTokenUseCase(JwtServicePort jwtService, UserRepositoryPort userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public AuthResponse execute(RefreshRequest request) {
        // 1. Validar el refresh token
        Claims claims = jwtService.getClaims(request.getRefreshToken());
        String userIdStr = claims.getSubject();

        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El ID del usuario en el token no es válido");
        }

        // 2. Buscar usuario en BD
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Generar nuevo access token
        String newAccessToken = jwtService.generateToken(user);

        // 4. Devolver respuesta
        return new AuthResponse(user.getId(), newAccessToken, request.getRefreshToken());
    }

}
