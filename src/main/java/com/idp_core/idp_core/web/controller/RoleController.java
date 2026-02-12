package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.AssignRoleRequest;
import com.idp_core.idp_core.application.usecase.LogAuditEventUseCase;
import com.idp_core.idp_core.application.usecase.RoleUseCase;

import com.idp_core.idp_core.web.common.ApiResponse;
import com.idp_core.idp_core.web.common.Auditable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    @Auditable(action = "ASSIGN_ROLE", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> assignRoleToUser(
            @RequestBody AssignRoleRequest request) {
            roleService.assignRole(request.getUserId(), request.getRoleId());
            log.info("Rol {} asignado al usuario {}", request.getRoleId(), request.getUserId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "OK", "Rol asignado correctamente")
            );
    }

    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    @Auditable(action = "REMOVE_ROLE", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> removeRoleFromUser(
            @RequestBody AssignRoleRequest request) {
            roleService.removeRole(request.getUserId(), request.getRoleId());
            log.info("Rol {} removido del usuario {}", request.getRoleId(), request.getUserId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "OK", "Rol removido correctamente")
            );
    }
}
