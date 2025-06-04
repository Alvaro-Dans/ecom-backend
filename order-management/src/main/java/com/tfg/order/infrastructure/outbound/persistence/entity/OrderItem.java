package com.tfg.order.infrastructure.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductItemOrdered itemOrdered;

    private java.math.BigDecimal price;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
