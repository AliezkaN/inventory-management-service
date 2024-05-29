package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.request.ProductRequest;
import com.nahorniak.inventorymanagementservice.dto.response.ProductDto;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.ProductEntity;
import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.persistance.StockEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.repository.ProductRepository;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import com.nahorniak.inventorymanagementservice.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final StockRepository stockRepository;

    private final SelmaMapper selma;


    public ProductDto getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(selma::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id {" + productId + "} not found"));
    }


    @Transactional
    public void createProduct(ProductRequest request, Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shopEntity = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setPrice(request.getPrice());
        productEntity.setShop(shopEntity);
        productEntity.setStatus("a");
        ProductEntity persistedProduct = productRepository.save(productEntity);
        StockEntity stockEntity = new StockEntity();
        stockEntity.setProduct(persistedProduct);
        stockEntity.setQuantity(request.getQuantity());
        stockRepository.save(stockEntity);
    }

    public ProductDto updateProduct(Long productId, ProductRequest request) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id {" + productId + "} not found"));
        Optional.ofNullable(request.getName()).ifPresent(productEntity::setName);
        Optional.ofNullable(request.getPrice()).ifPresent(productEntity::setPrice);
        Optional.ofNullable(request.getQuantity()).ifPresent(quantity -> productEntity.getStock().setQuantity(quantity));
        ProductEntity persistedEntity = productRepository.save(productEntity);
        return selma.toDto(persistedEntity);
    }

    public void deleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id {" + productId + "} not found"));
        productEntity.setStatus("d");
        productRepository.save(productEntity);
    }

    public List<ProductDto> getAll(Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shop = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
        return productRepository
                .findAllByShopId(shop.getId())
                .stream()
                .filter(x -> "a".equalsIgnoreCase(x.getStatus()))
                .map(selma::toDto)
                .toList();
    }
}
