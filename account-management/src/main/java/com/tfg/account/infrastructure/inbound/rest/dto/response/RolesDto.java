package com.tfg.account.infrastructure.inbound.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa un rol de usuario")
public record RolesDto(
        @Schema(description = "Identificador del rol", example = "1")
        Long id,

        @Schema(description = "Nombre del rol", example = "ADMIN")
        String name
) {}
