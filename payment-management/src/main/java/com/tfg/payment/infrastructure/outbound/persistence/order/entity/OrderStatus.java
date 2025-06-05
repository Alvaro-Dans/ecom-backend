package com.tfg.payment.infrastructure.outbound.persistence.order.entity;

public enum OrderStatus {
    PENDING,
    PAYMENT_RECEIVED,
    PAYMENT_FAILED,
    PAYMENT_MISMATCH,
    REFUNDED
}
