package com.tfg.backend.domain.repository;

import com.tfg.backend.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    void delete(UUID id);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    List<Product> findProductsPagedAndSorted(int page, int size, String sort);
}
