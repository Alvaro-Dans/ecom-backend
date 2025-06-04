package com.tfg.order.infrastructure.outbound.persistence.repository;

import com.tfg.order.infrastructure.outbound.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerEmail(String buyerEmail);
    Optional<Order> findByIdAndBuyerEmail(Long id, String buyerEmail);
}
