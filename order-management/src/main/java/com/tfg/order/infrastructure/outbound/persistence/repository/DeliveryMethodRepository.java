package com.tfg.order.infrastructure.outbound.persistence.repository;

import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethods, Long> {
}
