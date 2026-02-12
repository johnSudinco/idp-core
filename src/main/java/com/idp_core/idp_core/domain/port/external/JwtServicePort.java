package com.idp_core.idp_core.domain.port.external;


import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.infrastructure.adapter.security.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtServicePort {

    private final JwtProperties jwtProperties;
    private final Key key;

    public JwtServicePort(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
    public String getCorrelationId(String token) {
        Claims claims = getClaims(token);
        return claims.get("correlationId", String.class);
    }

    public String generateToken(User user) {
        List<String> roleNames = user.getRoles().stream()
                .map(ur -> ur.getRole().getName())
                .toList();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", roleNames)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(User user, List<String> permissions) {

        List<String> roleNames = user.getRoles().stream()
                .map(ur -> ur.getRole().getName())
                .toList();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", roleNames)
                .claim("permissions", permissions)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(User user, List<String> permissions, String correlationId) {

        List<String> roleNames = user.getRoles().stream()
                .map(ur -> ur.getRole().getName())
                .toList();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", roleNames)
                .claim("permissions", permissions)
                .claim("correlationId", correlationId)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)) // 7 días
                .signWith(key, SignatureAlgorithm.HS256) .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .requireIssuer(jwtProperties.getIssuer()) //  asegura que coincida el issuer
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Token expirado → devuelve false
            return false;
        } catch (JwtException e) {
            // Token inválido → devuelve false
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.getSubject());
    }

}

