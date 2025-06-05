package com.tfg.payment.infrastructure.outbound.persistence.delivery_method;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryMethodRepository extends JpaRepository<JpaDeliveryMethod, Integer> {
}
