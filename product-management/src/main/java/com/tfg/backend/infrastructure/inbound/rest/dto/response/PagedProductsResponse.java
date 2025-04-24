package com.tfg.backend.infrastructure.inbound.rest.dto.response;

import com.tfg.backend.domain.Product;

import java.util.List;

public record PagedProductsResponse(
        String pageIndex,
        String pageSize,
        String count,
        List<Product> data
) {
}
