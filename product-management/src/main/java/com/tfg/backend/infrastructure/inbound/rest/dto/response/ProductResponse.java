package com.tfg.backend.infrastructure.inbound.rest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String brand,
        String type,
        int quantityInStock,
        BigDecimal price,
        String pictureUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}