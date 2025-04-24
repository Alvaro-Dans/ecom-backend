package com.tfg.backend.infrastructure.outbound.persistence.repository;

import com.tfg.backend.domain.Product;
import com.tfg.backend.domain.repository.ProductRepository;
import com.tfg.backend.infrastructure.outbound.persistence.mapper.ProductJpaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgreSQLRepository implements ProductRepository {

    private final ProductsJpaRepository productsJpaRepository;


    public PostgreSQLRepository(ProductsJpaRepository productsJpaRepository) {
        this.productsJpaRepository = productsJpaRepository;
    }

    @Override
    public Product save(Product product) {
        return ProductJpaMapper.toDomain(productsJpaRepository.save(ProductJpaMapper.toJpa(product)));
    }

    @Override
    public void delete(UUID id) {
        productsJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productsJpaRepository.findById(id).map(ProductJpaMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productsJpaRepository.findAll().stream().map(ProductJpaMapper::toDomain).toList();
    }

    @Override
    public List<Product> findProductsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productsJpaRepository.findAll(pageable)
                .map(ProductJpaMapper::toDomain).toList();
    }
}
