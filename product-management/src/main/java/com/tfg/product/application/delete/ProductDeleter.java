package com.tfg.product.application.delete;

import com.tfg.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDeleter {

    private final ProductRepository productRepository;

    public ProductDeleter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(UUID id) {
        productRepository.delete(id);
    }
}
