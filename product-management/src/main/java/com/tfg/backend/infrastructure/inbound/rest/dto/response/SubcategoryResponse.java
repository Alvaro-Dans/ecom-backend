package com.tfg.backend.infrastructure.inbound.rest.dto.response;

public record SubcategoryResponse(
        String id,
        String name,
        CategoryResponse category
) {
}
