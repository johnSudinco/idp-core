package com.idp_core.idp_core.web.common;

import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.application.usecase.LogAuditEventUseCase;
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

    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void logAudit(JoinPoint joinPoint, Auditable auditable, Object result) {
        try {
            Long actorUserId = null;
            String correlationId = null;

            // Si el objeto resultante tiene getUserId, lo usamos
            if (result != null) {
                try {
                    actorUserId = (Long) result.getClass().getMethod("getUserId").invoke(result);
                } catch (Exception ignored) {}
                try {
                    correlationId = (String) result.getClass().getMethod("getCorrelationId").invoke(result);
                } catch (Exception ignored) {}
            }

            AuditLog logEntry = AuditLog.builder()
                    .actorUserId(actorUserId)
                    .action(auditable.action())
                    .targetType(auditable.targetType())
                    .targetId(actorUserId)
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
