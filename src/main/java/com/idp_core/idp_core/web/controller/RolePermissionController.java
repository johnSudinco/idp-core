package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.AssignPermissionRequest;
import com.idp_core.idp_core.application.usecase.RolePermissionUseCase;
import com.idp_core.idp_core.web.common.ApiResponse;
import com.idp_core.idp_core.web.common.Auditable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles/permissions")
public class RolePermissionController {

    private final RolePermissionUseCase rolePermissionUseCase;

    public RolePermissionController(RolePermissionUseCase rolePermissionUseCase) {
        this.rolePermissionUseCase = rolePermissionUseCase;
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('WEBMASTER')")
    @Auditable(action = "ASSIGN_ROLE_PERMISSION", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> assignPermission(
            @RequestBody AssignPermissionRequest request) {
        rolePermissionUseCase.grantPermission(
                request.getRoleId(),
                request.getPermissionId()
        );
        return ResponseEntity.ok(
                new ApiResponse<>(true, "OK", "Permiso asignado correctamente")
        );
    }
    @DeleteMapping("/revoke")
    @PreAuthorize("hasAuthority('WEBMASTER')")
    @Auditable(action = "REVOKE_ROLE_PERMISSION", targetType = "USER")
    public ResponseEntity<ApiResponse<String>> revokePermission(
            @RequestParam Long roleId,
            @RequestParam Long permissionId) {
        rolePermissionUseCase.revokePermission(roleId, permissionId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "OK", "Permiso revocado correctamente")
        );
    }
}
