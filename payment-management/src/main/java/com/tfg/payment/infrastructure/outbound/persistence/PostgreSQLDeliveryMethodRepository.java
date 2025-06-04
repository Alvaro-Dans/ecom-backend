package com.tfg.payment.infrastructure.outbound.persistence;

import com.tfg.payment.infrastructure.inbound.rest.dto.response.DeliveryMethodResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgreSQLDeliveryMethodRepository {

    private final JpaDeliveryMethodRepository repository;

    public PostgreSQLDeliveryMethodRepository(JpaDeliveryMethodRepository repository) {
        this.repository = repository;
    }

    public List<DeliveryMethodResponse> getDeliveryMethods() {
        List<DeliveryMethodResponse> response = new ArrayList<>();
        repository.findAll().forEach(deliveryMethod -> {
            response.add(new DeliveryMethodResponse(deliveryMethod.getShortName(),
                    deliveryMethod.getDeliveryTime(),
                    deliveryMethod.getDescription(),
                    deliveryMethod.getPrice(),
                    deliveryMethod.getId()));
        });
        return response;
    }
}
