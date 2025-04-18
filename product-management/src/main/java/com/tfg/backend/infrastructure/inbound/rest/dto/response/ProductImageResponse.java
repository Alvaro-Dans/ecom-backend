package com.tfg.backend.infrastructure.inbound.rest.dto.response;

public record ProductImageResponse(
        String id,
        String url,
        boolean isMain
) {
}
