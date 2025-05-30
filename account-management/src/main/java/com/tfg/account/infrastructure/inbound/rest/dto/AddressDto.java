package com.tfg.account.infrastructure.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos de dirección postal de un usuario.")
public record AddressDto(
        @Schema(description = "Línea 1 de la dirección", example = "Calle Falsa 123")
        @NotBlank String line1,

        @Schema(description = "Línea 2 de la dirección (opcional)", example = "2º Izquierda")
        String line2,

        @Schema(description = "Ciudad", example = "Madrid")
        @NotBlank String city,

        @Schema(description = "Provincia o estado", example = "Madrid")
        @NotBlank String state,

        @Schema(description = "Código postal", example = "28013")
        @NotBlank String postalCode,

        @Schema(description = "País", example = "España")
        @NotBlank String country
) {}