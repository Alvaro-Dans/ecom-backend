package com.tfg.account.infrastructure.inbound.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDto(
        @Schema(description = "Correo electrónico", example = "juan.perez@example.com") String email,
        @Schema(description = "Contraseña", example = "S3gur@123") String password
) {}