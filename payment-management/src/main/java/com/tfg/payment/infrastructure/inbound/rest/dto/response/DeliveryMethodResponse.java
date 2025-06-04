package com.tfg.payment.infrastructure.inbound.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeliveryMethodResponse {

    private String shortName;
    private String deliveryTime;
    private String description;
    private Double price;
    private int id;
}
