package com.tfg.account.infrastructure.inbound.rest.dto.response;

import com.tfg.account.infrastructure.inbound.rest.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Información pública del usuario autenticado.")
public record UserInfoDto(
        @Schema(description = "Nombre", example = "Juan") String firstName,
        @Schema(description = "Apellido", example = "Pérez") String lastName,
        @Schema(description = "Correo electrónico", example = "juan.perez@example.com") String email,
        @Schema(description = "Dirección postal", implementation = AddressDto.class) AddressDto address,
        @ArraySchema(schema = @Schema(description = "Roles asociados al usuario", example = "[\"USER\"]"))
        List<RolesDto> roles
) {}