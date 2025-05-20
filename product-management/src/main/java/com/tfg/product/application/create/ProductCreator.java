package com.tfg.product.application.create;

import com.tfg.product.domain.Product;
import com.tfg.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCreator {

    private final ProductRepository productRepository;

    public ProductCreator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
}
