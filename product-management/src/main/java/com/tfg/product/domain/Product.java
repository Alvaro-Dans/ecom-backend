package com.tfg.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Product {

    private Long id;

    private String name;

    private String description;

    private String brand;

    private String category;

    private Integer stock;

    private BigDecimal price;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

