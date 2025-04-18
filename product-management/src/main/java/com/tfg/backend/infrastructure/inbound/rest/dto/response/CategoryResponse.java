package com.tfg.backend.infrastructure.inbound.rest.dto.response;

public record CategoryResponse(
        String id,
        String name,
        String description
) {
}
