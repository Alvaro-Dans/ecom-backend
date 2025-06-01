package com.tfg.product.domain.repository;

import com.tfg.product.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    void delete(Long id);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findProductsPagedAndSorted(int page, int size, String sort);
}
