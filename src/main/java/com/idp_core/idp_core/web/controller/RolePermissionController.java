package com.idp_core.idp_core.web.controller;

import com.idp_core.idp_core.application.dto.AssignPermissionRequest;
import com.idp_core.idp_core.application.usecase.RolePermissionUseCase;
import com.idp_core.idp_core.web.common.ApiResponse;
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

    /**
     * Asignar permiso a un rol
     */
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('WEBMASTER')")
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

    /**
     * Revocar permiso a un rol
     */
    @DeleteMapping("/revoke")
    @PreAuthorize("hasAuthority('WEBMASTER')")
    public ResponseEntity<ApiResponse<String>> revokePermission(
            @RequestParam Long roleId,
            @RequestParam Long permissionId) {

        rolePermissionUseCase.revokePermission(roleId, permissionId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "OK", "Permiso revocado correctamente")
        );
    }
}
