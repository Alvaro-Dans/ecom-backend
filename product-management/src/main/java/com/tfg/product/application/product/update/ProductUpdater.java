package com.tfg.product.application.product.update;

import com.tfg.product.domain.Product;
import com.tfg.product.domain.repository.ProductRepository;
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
