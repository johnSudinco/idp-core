package com.idp_core.idp_core.application.port;

import com.idp_core.idp_core.domain.model.Client;
import com.idp_core.idp_core.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {

    private final String secretKey = "super-secret-key";

    public String generateAccessToken(User user, Client client) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("client_id", client.getClientId())
                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
