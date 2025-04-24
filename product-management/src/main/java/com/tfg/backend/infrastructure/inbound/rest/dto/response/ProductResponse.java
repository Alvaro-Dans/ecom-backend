package com.tfg.backend.infrastructure.inbound.rest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        String brand,
        String category,
        int stock,
        BigDecimal price,
        String image,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}