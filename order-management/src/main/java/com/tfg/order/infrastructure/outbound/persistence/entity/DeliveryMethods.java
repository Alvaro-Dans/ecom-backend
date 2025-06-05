package com.tfg.order.infrastructure.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "delivery_methods")
@Data
public class DeliveryMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortName;
    private String deliveryTime;
    private String description;
    private java.math.BigDecimal price;
}
