package com.tfg.backend.infrastructure.inbound.rest.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        BrandResponse brand,
        CategoryResponse category,
        SubcategoryResponse subcategory,
        Integer stock,
        List<PriceResponse> prices,
        List<ProductImageResponse> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
