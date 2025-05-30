package com.tfg.account.infrastructure.inbound.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthStatusDto(
        @Schema(description = "Indica si el usuario está autenticado", example = "true") boolean isAuthenticated
) {}
