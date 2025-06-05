package com.tfg.order.application.dto;

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
