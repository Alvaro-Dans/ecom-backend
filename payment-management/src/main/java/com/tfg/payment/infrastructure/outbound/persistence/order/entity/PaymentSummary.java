package com.tfg.payment.infrastructure.outbound.persistence.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class PaymentSummary {
    private Integer last4;
    private String brand;
    private Integer expMonth;
    private Integer expYear;
}
