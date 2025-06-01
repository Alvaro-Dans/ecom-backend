package com.tfg.product.infrastructure.inbound.rest.controller;

import com.tfg.product.application.create.ProductCreator;
import com.tfg.product.application.delete.ProductDeleter;
import com.tfg.product.application.find.ProductFinder;
import com.tfg.product.application.update.ProductUpdater;
import com.tfg.product.infrastructure.inbound.rest.doc.ProductApi;
import com.tfg.product.infrastructure.inbound.rest.dto.request.ProductRequest;
import com.tfg.product.infrastructure.inbound.rest.dto.response.PagedProductsResponse;
import com.tfg.product.infrastructure.inbound.rest.dto.response.ProductResponse;
import com.tfg.product.infrastructure.mapper.ProductDtoMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
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
    public ProductResponse getProduct(Long id) {
        return productDtoMapper.toResponse(this.productFinder.findById(id));
    }

    @Override
    public PagedProductsResponse getProductsPagedAndSorted(int page, int size, String sort) {
        List<ProductResponse> productListResponse = productFinder.findProductsPagedAndSorted(page, size, sort).stream().map(productDtoMapper::toResponse).toList();
        return new PagedProductsResponse(page, size, productFinder.count(), productListResponse);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return productDtoMapper.toResponse(productUpdater.updateProduct(productDtoMapper.toDomain(productRequest)));
    }

    @Override
    public void deleteProduct(Long id) {
        this.productDeleter.deleteProduct(id);
    }

    @Override
    public List<String> getAllBrands() {
        return new HashSet<>(productFinder.getAllBrands()).stream().sorted().toList();
    }

    @Override
    public List<String> getAllTypes() {
        return new HashSet<>(productFinder.getAllTypes()).stream().sorted().toList();
    }
}
