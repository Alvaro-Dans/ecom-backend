package com.tfg.order.infrastructure.inbound.rest.dto.request;

import com.tfg.order.infrastructure.outbound.persistence.entity.PaymentSummary;
import com.tfg.order.infrastructure.outbound.persistence.entity.ShippingAddress;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotNull
    private String cartId;

    @NotNull
    private Long deliveryMethodId;

    private BigDecimal discount;

    @NotNull
    private ShippingAddress shippingAddress;

    @NotNull
    private PaymentSummary paymentSummary;


}
