package com.tfg.payment.infrastructure.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "delivery_methods")
@Data
public class JpaDeliveryMethod {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String shortName;

    @Column
    private String deliveryTime;

    @Column
    private String description;

    @Column
    private Double price;

}
