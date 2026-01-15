package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.AssignRoleRequest;
import com.idp_core.idp_core.application.usecase.LogAuditEventUseCase;
import com.idp_core.idp_core.application.usecase.RoleUseCase;

import com.idp_core.idp_core.domain.model.AuditLog;
import com.idp_core.idp_core.web.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleUseCase roleService;
    private final LogAuditEventUseCase logAuditEventUseCase;

    public RoleController(RoleUseCase roleService,
                          LogAuditEventUseCase logAuditEventUseCase) {
        this.roleService = roleService;
        this.logAuditEventUseCase = logAuditEventUseCase;
    }

    @PostMapping("/assign")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> assignRoleToUser(
            @RequestBody AssignRoleRequest request) {


            roleService.assignRole(request.getUserId(), request.getRoleId());

            log.info("Rol {} asignado al usuario {}", request.getRoleId(), request.getUserId());

            logAuditEventUseCase.execute(
                    AuditLog.builder()
                            .action("ASSIGN_ROLE")
                            .targetType("USER")
                            .targetId(request.getUserId())
                            .metadata("Rol asignado: " + request.getRoleId())
                            .createdAt(LocalDateTime.now())
                            .build()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "OK", "Rol asignado correctamente")
            );


    }

    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> removeRoleFromUser(
            @RequestBody AssignRoleRequest request) {


            roleService.removeRole(request.getUserId(), request.getRoleId());

            log.info("Rol {} removido del usuario {}", request.getRoleId(), request.getUserId());

            logAuditEventUseCase.execute(
                    AuditLog.builder()
                            .action("REMOVE_ROLE")
                            .targetType("USER")
                            .targetId(request.getUserId())
                            .metadata("Rol removido: " + request.getRoleId())
                            .createdAt(LocalDateTime.now())
                            .build()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "OK", "Rol removido correctamente")
            );


    }
}
