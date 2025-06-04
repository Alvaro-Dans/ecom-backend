package com.tfg.order.infrastructure.outbound.persistence.entity;

public enum OrderStatus {
    PENDING,
    PAYMENT_RECEIVED,
    PAYMENT_FAILED,
    PAYMENT_MISMATCH,
    REFUNDED
}
