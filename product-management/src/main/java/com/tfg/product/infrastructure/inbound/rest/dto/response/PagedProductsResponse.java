package com.tfg.product.infrastructure.inbound.rest.dto.response;

import java.util.List;

public record PagedProductsResponse(
        int pageIndex,
        int pageSize,
        int count,
        List<ProductResponse> data
) {
}
