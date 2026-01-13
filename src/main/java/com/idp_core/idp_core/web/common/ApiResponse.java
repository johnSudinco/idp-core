package com.idp_core.idp_core.web.common;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) {}
