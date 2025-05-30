package com.tfg.account.infrastructure.inbound.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para el registro de un nuevo usuario.")
public record RegisterDto(
        @Schema(description = "Nombre del usuario", example = "Juan")
        @NotBlank String firstName,

        @Schema(description = "Apellido del usuario", example = "Pérez")
        @NotBlank String lastName,

        @Schema(description = "Correo electrónico único y válido", example = "juan.perez@example.com")
        @Email @NotBlank String email,

        @Schema(description = "Contraseña segura con al menos 8 caracteres", example = "P@ssw0rd!23")
        @NotBlank String password
) {}
