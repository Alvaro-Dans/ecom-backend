package com.tfg.product.infrastructure.outbound.persistence.repository;

import com.tfg.product.infrastructure.outbound.persistence.entity.JpaProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsJpaRepository extends JpaRepository<JpaProduct, Long> {


    Page<JpaProduct> findAll(Pageable pageable);
}
