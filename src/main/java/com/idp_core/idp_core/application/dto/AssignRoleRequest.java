package com.idp_core.idp_core.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleRequest {
    private Long userId;
    private Long roleId;
}
