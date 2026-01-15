package com.idp_core.idp_core.infrastructure.adapter.security;

import com.idp_core.idp_core.application.port.SessionService;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServicePort jwtService;
    private final SessionService sessionService;

    public JwtAuthenticationFilter(
            JwtServicePort jwtService,
            SessionService sessionService
    ) {
        this.jwtService = jwtService;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Endpoints públicos
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtService.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (sessionService.isTokenRevoked(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims claims = jwtService.getClaims(token);
            String userId = claims.getSubject();

            Object rolesClaim = claims.get("roles");

            List<GrantedAuthority> authorities =
                    (rolesClaim instanceof List<?> roles)
                            ? roles.stream()
                            .map(Object::toString)
                            .map(r -> r.toUpperCase().trim())
                            .map(r -> r.replace(" ", "_").replace(".", ""))
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .collect(Collectors.toList())
                            : Collections.emptyList();


            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            authorities
                    );
            // IMPORTANTE: asignar detalles de la request
            authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // NO rompas la cadena
        }

        filterChain.doFilter(request, response);
    }

}
