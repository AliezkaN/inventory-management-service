package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.request.ProductRequest;
import com.nahorniak.inventorymanagementservice.dto.response.ProductDto;
import com.nahorniak.inventorymanagementservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<ProductDto> getAll(Authentication connectedUser) {
        return service.getAll(connectedUser);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductByProductId(@PathVariable Long productId) {
        return service.getProductByProductId(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest request, Authentication connectedUser) {
        service.createProduct(request, connectedUser);
    }

    @PutMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        return service.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
    }
}
