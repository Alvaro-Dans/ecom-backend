package com.tfg.backend.domain.repository;

import com.tfg.backend.domain.Product;
import com.tfg.backend.infrastructure.outbound.persistence.entity.JpaProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    void delete(UUID id);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findProductsPaged(int page, int size);
}
