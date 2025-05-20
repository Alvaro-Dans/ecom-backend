package com.tfg.product.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequest(
        @NotNull(message = "El ID del producto es obligatorio")
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "La marca es obligatoria")
        String brand,

        @NotNull(message = "La categoría es obligatoria")
        String category,

        @PositiveOrZero(message = "El stock no puede ser negativo")
        int stock,

        @NotNull(message = "El precio es obligatorio")
        BigDecimal price,

        String image,

        @NotNull(message = "La fecha de creación es obligatoria")
        LocalDateTime createdAt,

        @NotNull(message = "La fecha de última modificación es obligatoria")
        LocalDateTime updatedAt
) {
}
