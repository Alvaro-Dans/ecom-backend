package com.tfg.product.infrastructure.outbound.persistence.repository;

import com.tfg.product.domain.Product;
import com.tfg.product.domain.repository.ProductRepository;
import com.tfg.product.infrastructure.outbound.persistence.mapper.ProductJpaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<Product> findProductsPagedAndSorted(int page, int size, String sort) {
        Sort sortBy;

        // Aseguramos los campos válidos
        if (sort.equalsIgnoreCase("priceAsc")) {
            sortBy = Sort.by(Sort.Direction.ASC, "price");
        } else if (sort.equalsIgnoreCase("priceDesc")) {
            // Asegúrate que el atributo 'price.amount' esté mapeado correctamente
            sortBy = Sort.by(Sort.Direction.DESC, "price");
        } else {
            // Fallback por defecto si el parámetro no es válido
            sortBy = Sort.by("name");
        }

        // TODO filters
        /* Specification
        Specification<JpaProduct> spec = Specification.where(null);

        if (brandIds != null && !brandIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("brand").get("id").in(brandIds));
        }

        if (typeIds != null && !typeIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("category").get("id").in(typeIds)); // o subcategory
        }*/

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);
        return productsJpaRepository.findAll(pageable)
                .map(ProductJpaMapper::toDomain).toList();
    }
}
