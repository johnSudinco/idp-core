package com.idp_core.idp_core.web.common;

import com.idp_core.idp_core.application.dto.AuthResponse;
import com.idp_core.idp_core.application.dto.LoginRequest;
import com.idp_core.idp_core.application.dto.ResetPasswordRequest;
import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.application.usecase.LogAuditEventUseCase;
import com.idp_core.idp_core.domain.port.external.JwtServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final LogAuditEventUseCase logAuditEventUseCase;
    private final HttpServletRequest request;
    private final JwtServicePort jwtService;

    /**
     * Obtiene el actor (usuario autenticado) desde el JWT.
     */
    private Long resolveActorUserId(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return jwtService.getUserIdFromToken(token);
            }
        } catch (Exception e) {
            log.warn("No se pudo resolver actorUserId desde JWT", e);
        }
        return null;
    }

    /**
     * Obtiene el targetId desde los argumentos del método interceptado.
     * Usa instanceof para DTOs conocidos, y reflection como fallback.
     */
    private Long resolveTargetId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ResetPasswordRequest req) {
                return req.getUserId();
            }
            if (arg instanceof LoginRequest req) {
                // En login, el request no trae userId, se resolverá en el result
                continue;
            }
            try {
                Object value = arg.getClass().getMethod("getUserId").invoke(arg);
                if (value instanceof Long id) {
                    return id;
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void logAudit(JoinPoint joinPoint, Auditable auditable, Object result) {
        try {
            Long actorUserId = resolveActorUserId(request);
            Long targetId = resolveTargetId(joinPoint.getArgs());

            // Caso especial: login → IDs vienen en el AuthResponse
            if (result instanceof AuthResponse resp) {
                actorUserId = resp.getUserId();
                targetId = resp.getUserId();
            }

            // CorrelationId: primero intenta desde header, luego desde result
            String correlationId = request.getHeader("X-Correlation-Id");
            if (correlationId == null && result != null) {
                try {
                    correlationId = (String) result.getClass().getMethod("getCorrelationId").invoke(result);
                } catch (Exception ignored) {}
            }

            AuditLog logEntry = AuditLog.builder()
                    .actorUserId(actorUserId)
                    .action(auditable.action())
                    .targetType(auditable.targetType())
                    .targetId(targetId != null ? targetId : actorUserId)
                    .correlationId(correlationId)
                    .ipAddress(request.getRemoteAddr())
                    .userAgent(request.getHeader("User-Agent"))
                    .metadata("Acción ejecutada: " + auditable.action())
                    .createdAt(LocalDateTime.now())
                    .build();

            logAuditEventUseCase.execute(logEntry);

        } catch (Exception e) {
            log.error("Error registrando auditoría automática", e);
        }
    }
}
