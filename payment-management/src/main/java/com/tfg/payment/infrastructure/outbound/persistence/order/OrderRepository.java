package com.tfg.payment.infrastructure.outbound.persistence.order;

import com.tfg.payment.infrastructure.outbound.persistence.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByPaymentIntentId(String paymentIntentId);
}
