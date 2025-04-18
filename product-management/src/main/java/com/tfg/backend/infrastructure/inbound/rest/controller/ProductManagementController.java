package com.tfg.backend.infrastructure.inbound.rest.controller;

import com.tfg.backend.infrastructure.inbound.rest.doc.ProductApi;
import com.tfg.backend.infrastructure.inbound.rest.dto.request.ProductRequest;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductManagementController implements ProductApi {

    @Override
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponseDTO> getProduct(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponseDTO> updateProduct(UUID id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        return null;
    }
}
