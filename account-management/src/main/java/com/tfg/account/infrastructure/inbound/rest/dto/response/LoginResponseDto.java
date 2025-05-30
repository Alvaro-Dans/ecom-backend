package com.tfg.account.infrastructure.inbound.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDto(
        @Schema(description = "Mensaje de éxito al iniciar sesión", example = "Login successful") String message
) {}
