package com.tfg.payment.infrastructure.outbound.persistence.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProductItemOrdered {
    private Long productId;
    private String productName;
    private String pictureUrl;
}
