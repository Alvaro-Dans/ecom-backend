package com.tfg.order.infrastructure.outbound.persistence.repository;

import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
}
