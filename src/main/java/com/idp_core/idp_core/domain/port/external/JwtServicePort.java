package com.idp_core.idp_core.domain.port.external;

import com.idp_core.idp_core.domain.model.User;
import io.jsonwebtoken.Claims;
import java.util.List;

public interface JwtServicePort {
    String generateToken(User user, List<String> permissions);
    String generateRefreshToken(User user);
    boolean validateToken(String token);
    Claims getClaims(String token);
    Long getUserIdFromToken(String token);
    String getCorrelationId(String token);
}
