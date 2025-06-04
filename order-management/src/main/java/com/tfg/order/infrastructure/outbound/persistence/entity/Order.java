package com.tfg.order.infrastructure.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderDate = new Date();

    private String buyerEmail;

    @Embedded
    private ShippingAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryMethod deliveryMethod;

    @Embedded
    private PaymentSummary paymentSummary;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private java.math.BigDecimal subtotal;
    private java.math.BigDecimal discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    private String paymentIntentId;

    public java.math.BigDecimal getTotal() {
        return subtotal.subtract(discount).add(deliveryMethod.getPrice());
    }
}
