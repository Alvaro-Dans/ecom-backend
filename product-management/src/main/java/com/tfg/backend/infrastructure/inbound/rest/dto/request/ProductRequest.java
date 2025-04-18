package com.tfg.backend.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record ProductRequest(
        @NotBlank(message = "El nombre del producto es obligatorio")
        String name,

        @NotBlank(message = "La descripción del producto es obligatoria")
        String description,

        @NotNull(message = "La marca es obligatoria")
        BrandRequest brand,

        @NotNull(message = "La categoría es obligatoria")
        CategoryRequest category,

        SubcategoryRequest subcategory,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser menor a 0")
        Integer stock,

        @NotNull(message = "Los precios son obligatorios")
        List<PriceRequest> prices,

        List<ProductImageRequest> images

) {}

