package com.tfg.backend.infrastructure.inbound.rest.dto.response;

public record BrandResponse(
        String id,
        String name,
        String logoUrl
) {
}
