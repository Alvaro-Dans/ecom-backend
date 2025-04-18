package com.tfg.backend.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductImageRequest(
        @NotBlank(message = "La URL de la imagen es obligatoria")
        String url,

        boolean isMain
) {
}
