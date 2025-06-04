package com.tfg.payment.infrastructure.inbound.rest.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartRequest {

    private String id;
    private List<CartProductRequest> items;
    private int deliveryMethodId;
    private String clientSecret;
    private String paymentIntentId;
    private String coupon;
}

