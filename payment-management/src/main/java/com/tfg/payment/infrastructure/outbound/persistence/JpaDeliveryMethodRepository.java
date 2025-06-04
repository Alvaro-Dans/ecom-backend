package com.tfg.payment.infrastructure.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryMethodRepository extends JpaRepository<JpaDeliveryMethod, Integer> {
}
