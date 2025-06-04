package com.tfg.product.application.product.delete;

import com.tfg.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDeleter {

    private final ProductRepository productRepository;

    public ProductDeleter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}
