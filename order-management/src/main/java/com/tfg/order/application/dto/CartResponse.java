package com.tfg.order.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CartResponse {

    private String id;
    private List<CartProductRequest> items;
    private int deliveryMethodId;
    private String clientSecret;
    private String paymentIntentId;
    private String coupon;
}

