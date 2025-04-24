package com.tfg.backend.application.update;

import com.tfg.backend.domain.Product;
import com.tfg.backend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdater {

    private final ProductRepository productRepository;

    public ProductUpdater(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
