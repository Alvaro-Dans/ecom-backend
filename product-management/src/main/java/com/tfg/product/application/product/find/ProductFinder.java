package com.tfg.product.application.product.find;

import com.tfg.product.domain.Product;
import com.tfg.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductFinder {

    private final ProductRepository productRepository;

    public ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findProductsPagedAndSorted(int page, int size, String sort) {
        return productRepository.findProductsPagedAndSorted(page, size, sort);
    }

    public Product findById(Long id) {
        // TODO manejar excepcion
        return productRepository.findById(id).orElse(null);
    }

    public int count() {
        return productRepository.findAll().size();
    }

    public List<String> getAllBrands() {
        return productRepository.findAll().stream().map(Product::getBrand).toList();
    }

    public List<String> getAllTypes() {
        return productRepository.findAll().stream().map(Product::getCategory).toList();
    }
}
