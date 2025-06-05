package com.tfg.payment.infrastructure.outbound.persistence.order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderDate = new Date();

    private String buyerEmail;

    @Embedded
    private ShippingAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_methods_id")
    private DeliveryMethods deliveryMethods;

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
        return subtotal.subtract(discount).add(deliveryMethods.getPrice());
    }
}
