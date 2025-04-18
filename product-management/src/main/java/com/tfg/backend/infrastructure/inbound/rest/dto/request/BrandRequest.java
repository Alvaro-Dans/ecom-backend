package com.tfg.backend.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(
        @NotBlank(message = "El nombre de la marca es obligatorio")
        String name,

        @NotBlank(message = "La URL del logo de la marca es obligatoria")
        String logoUrl
) {
}
