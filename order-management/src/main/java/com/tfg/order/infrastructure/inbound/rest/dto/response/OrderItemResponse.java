package com.tfg.order.infrastructure.inbound.rest.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private String pictureUrl;
    private BigDecimal price;
    private Integer quantity;
}
