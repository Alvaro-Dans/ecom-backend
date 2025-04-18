package com.tfg.backend.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubcategoryRequest(
        @NotBlank(message = "El nombre de la subcategoría es obligatorio")
        String name,

        @NotNull(message = "La categoría es obligatoria")
        CategoryRequest category
) {
}
