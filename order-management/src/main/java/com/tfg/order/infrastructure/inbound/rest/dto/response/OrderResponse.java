package com.tfg.order.infrastructure.inbound.rest.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private String buyerEmail;
    private com.tfg.order.infrastructure.outbound.persistence.entity.ShippingAddress shippingAddress;
    private String deliveryMethod;
    private BigDecimal shippingPrice;
    private com.tfg.order.infrastructure.outbound.persistence.entity.PaymentSummary paymentSummary;
    private List<OrderItemResponse> orderItems;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private String status;
    private BigDecimal total;
    private String paymentIntentId;
}
