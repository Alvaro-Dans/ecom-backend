package com.tfg.backend.application.find;

import com.tfg.backend.domain.Product;
import com.tfg.backend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductFinder {

    private final ProductRepository productRepository;

    public ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findProductsPaged(int page, int size) {
        return productRepository.findProductsPaged(page, size);
    }

    public Product findById(UUID id) {
        // TODO manejar excepcion
        return productRepository.findById(id).orElse(null);
    }
}
