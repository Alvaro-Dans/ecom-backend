package com.tfg.payment.infrastructure.inbound.rest.dto.request;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,

        String name,

        String description,

        String brand,

        String category,

        int stock,

        BigDecimal price,

        String image
) {
}
