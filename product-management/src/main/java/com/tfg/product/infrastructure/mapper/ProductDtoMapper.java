package com.tfg.product.infrastructure.mapper;

import com.tfg.product.domain.*;
import com.tfg.product.infrastructure.inbound.rest.dto.request.*;
import com.tfg.product.infrastructure.inbound.rest.dto.response.*;
import org.springframework.stereotype.Component;

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
