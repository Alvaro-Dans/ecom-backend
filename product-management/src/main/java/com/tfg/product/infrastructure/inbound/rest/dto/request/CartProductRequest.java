package com.tfg.product.infrastructure.inbound.rest.dto.request;

import java.math.BigDecimal;

public record CartProductRequest (

    Long productId,

    String productName,

    String brand,

    String category,

    int quantity,

    BigDecimal price,

    String pictureUrl
){}
