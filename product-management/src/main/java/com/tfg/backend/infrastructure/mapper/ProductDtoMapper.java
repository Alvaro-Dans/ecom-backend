package com.tfg.backend.infrastructure.mapper;

import com.tfg.backend.domain.*;
import com.tfg.backend.infrastructure.inbound.rest.dto.request.*;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getCategory(),
                product.getStock(),
                product.getPrice(),
                product.getImage(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }



    public Product toDomain(ProductRequest request) {
        return new Product(
                request.id(),
                request.name(),
                request.description(),
                request.brand(),
                request.category(),
                request.stock(),
                request.price(),
                request.image(),
                request.createdAt(),
                request.updatedAt()
        );
    }

}
