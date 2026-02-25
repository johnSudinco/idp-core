package com.idp_core.idp_core.infrastructure.adapter.security;

import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import com.idp_core.idp_core.web.common.DataEncryptor;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtServiceAdapter implements JwtServicePort {

    private final JwtProperties jwtProperties;
    private final Key key;
    private final DataEncryptor dataEncryptor;

    public JwtServiceAdapter(JwtProperties jwtProperties,DataEncryptor dataEncryptor) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.dataEncryptor=dataEncryptor;
    }

    @Override
    public String generateToken(User user, List<String> permissions) {
        List<String> roleNames = user.getRoles().stream()
                .map(ur -> ur.getRole().getName())
                .toList();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("email", (user.getEmail()))
                .claim("roles", roleNames)
                .claim("permissions", permissions)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("client_id", 1L)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .requireIssuer(jwtProperties.getIssuer())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    @Override
    public String getCorrelationId(String token) {
        return getClaims(token).get("correlationId", String.class);
    }
}
