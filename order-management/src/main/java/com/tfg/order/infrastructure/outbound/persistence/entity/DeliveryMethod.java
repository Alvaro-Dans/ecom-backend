package com.tfg.order.infrastructure.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortName;
    private String deliveryTime;
    private String description;
    private java.math.BigDecimal price;
}
