package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.persistance.*;
import com.nahorniak.inventorymanagementservice.repository.OrderRepository;
import com.nahorniak.inventorymanagementservice.repository.ProductRepository;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import com.nahorniak.inventorymanagementservice.service.fetchers.PredictionFetcher;
import com.nahorniak.inventorymanagementservice.utils.OrderStatsCollector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionFetcher predictionFetcher;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderStatsCollector orderStatsCollector;

    public PredictionResponse predict(Authentication connectedUser) {
        
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shop = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));

        List<OrderEntity> orders = orderRepository.findAllByShopId(shop.getId());
        PredictionFetcher.PredictionApiResponse fetchedPrediction = predictionFetcher.fetch(orderStatsCollector.collect(orders));

        Map<Long, ProductEntity> products = productRepository.findAllByIdIn(fetchedPrediction.getPrediction().keySet())
                .stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));


        return fetchedPrediction.getPrediction()
                .entrySet()
                .stream()
                .map(entry -> {
                    PredictionResponse.PredictionProduct predictionProduct = new PredictionResponse.PredictionProduct();
                    Optional<ProductEntity> productOpt = Optional.ofNullable(products.get(entry.getKey()));
                    predictionProduct.setProductId(entry.getKey());
                    predictionProduct.setPredictedQuantity(entry.getValue());
                    productOpt.map(ProductEntity::getName).ifPresent(predictionProduct::setProductName);
                    productOpt.map(ProductEntity::getStock).map(StockEntity::getQuantity).ifPresent(predictionProduct::setActualQuantity);
                    return predictionProduct;
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), PredictionResponse::new));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PredictionResponse {
        private List<PredictionProduct> predictions;

        @Data
        public static class PredictionProduct {
            private Long productId;
            private String productName;
            private Integer actualQuantity;
            private Integer predictedQuantity;
        }
    }
}
