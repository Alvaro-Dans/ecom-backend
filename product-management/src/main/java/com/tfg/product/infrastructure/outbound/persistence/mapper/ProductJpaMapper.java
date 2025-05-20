package com.tfg.product.infrastructure.outbound.persistence.mapper;

import com.tfg.product.domain.*;
import com.tfg.product.infrastructure.outbound.persistence.entity.*;

public class ProductJpaMapper {

    // ----------- TO DOMAIN ------------

    public static Product toDomain(JpaProduct jpaProduct) {
        return new Product(
                jpaProduct.getId(),
                jpaProduct.getName(),
                jpaProduct.getDescription(),
                jpaProduct.getBrand(),
                jpaProduct.getCategory(),
                jpaProduct.getStock(),
                jpaProduct.getPrice(),
                jpaProduct.getImage(),
                jpaProduct.getCreatedAt(),
                jpaProduct.getUpdatedAt()
        );
    }

    // ----------- TO JPA ------------

    public static JpaProduct toJpa(Product product) {
        JpaProduct jpaProduct = new JpaProduct();
        jpaProduct.setId(product.getId());
        jpaProduct.setName(product.getName());
        jpaProduct.setDescription(product.getDescription());
        jpaProduct.setBrand(product.getBrand());
        jpaProduct.setCategory(product.getCategory());
        jpaProduct.setImage(product.getImage());
        jpaProduct.setPrice(product.getPrice());
        jpaProduct.setCreatedAt(product.getCreatedAt());
        jpaProduct.setUpdatedAt(product.getUpdatedAt());
        return jpaProduct;
    }

}