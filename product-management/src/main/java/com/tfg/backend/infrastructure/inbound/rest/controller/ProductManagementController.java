package com.tfg.backend.infrastructure.inbound.rest.controller;

import com.tfg.backend.application.create.ProductCreator;
import com.tfg.backend.application.delete.ProductDeleter;
import com.tfg.backend.application.find.ProductFinder;
import com.tfg.backend.application.update.ProductUpdater;
import com.tfg.backend.infrastructure.inbound.rest.doc.ProductApi;
import com.tfg.backend.infrastructure.inbound.rest.dto.request.ProductRequest;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.PagedProductsResponse;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.ProductResponse;
import com.tfg.backend.infrastructure.mapper.ProductDtoMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductManagementController implements ProductApi {

    private final ProductCreator productCreator;
    private final ProductFinder productFinder;
    private final ProductUpdater productUpdater;
    private final ProductDeleter productDeleter;

    private final ProductDtoMapper productDtoMapper;

    public ProductManagementController(ProductCreator productCreator, ProductFinder productFinder,ProductUpdater productUpdater,ProductDeleter productDeleter, ProductDtoMapper productDtoMapper) {
        this.productCreator = productCreator;
        this.productFinder = productFinder;
        this.productUpdater = productUpdater;
        this.productDeleter = productDeleter;
        this.productDtoMapper = productDtoMapper;
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        productCreator.createProduct(productDtoMapper.toDomain(productRequest));
    }

    @Override
    public ProductResponse getProduct(UUID id) {
        return productDtoMapper.toResponse(this.productFinder.findById(id));
    }

    @Override
    public PagedProductsResponse getProductsPaged(int page, int size) {
        productFinder.findProductsPaged(page, size).stream().map(productDtoMapper::toResponse).toList();
        return null;
    }

    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        return productDtoMapper.toResponse(productUpdater.updateProduct(productDtoMapper.toDomain(productRequest)));
    }

    @Override
    public void deleteProduct(UUID id) {
        this.productDeleter.deleteProduct(id);
    }
}
