package com.tfg.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Product {

    private UUID id;

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

