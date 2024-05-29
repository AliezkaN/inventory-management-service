package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.request.OrderRequest;
import com.nahorniak.inventorymanagementservice.dto.request.OrderStatsRequest;
import com.nahorniak.inventorymanagementservice.dto.response.OrderDto;
import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.*;
import com.nahorniak.inventorymanagementservice.repository.OrderRepository;
import com.nahorniak.inventorymanagementservice.repository.ProductRepository;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import com.nahorniak.inventorymanagementservice.repository.StockRepository;
import com.nahorniak.inventorymanagementservice.utils.OrderStatsCollector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final OrderStatsCollector orderStatsCollector;


    private final SelmaMapper selma;

    public List<OrderDto> getAllByUser(Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shopEntity = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
        return orderRepository
                .findAllByShopId(shopEntity.getId())
                .stream()
                .map(selma::toDto)
                .toList();
    }

    public OrderDto getOrderById(Long orderId) {
        OrderEntity storedEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id (" + orderId + ") not found!"));
        return selma.toDto(storedEntity);
    }

    @Transactional
    public void createOrder(OrderRequest request, Authentication connectedUser) {
        OrderEntity orderEntity = new OrderEntity();
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shopEntity = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
        List<OrderProductsEntity> orderProducts = request.getOrderDetails()
                .stream()
                .map(orderDetail -> {

                    OrderProductsEntity orderProductsEntity = new OrderProductsEntity();

                    ProductEntity productEntity = productRepository.findById(orderDetail.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product with id {" + orderDetail.getProductId() + "} not found"));

                    StockEntity stockEntity = stockRepository.findByProductId(productEntity.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Stock info by shopId (" + shopEntity.getId() + ") and productId (" + productEntity.getId() + ") not found!"));

                    if (stockEntity.getQuantity() < orderDetail.getQuantity()) {
                        throw new IllegalArgumentException("there is not plenty of product with id (" + productEntity.getId() + ") on stock!");
                    }

                    stockEntity.extractFromQuantity(orderDetail.getQuantity());

                    orderProductsEntity.setProduct(productEntity);
                    orderProductsEntity.setQuantity(orderDetail.getQuantity());
                    orderProductsEntity.setPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));

                    return orderProductsEntity;
                })
                .toList();

        orderEntity.setShop(shopEntity);
        orderEntity.setOrderProducts(orderProducts);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setTotalAmount(orderProducts.stream().map(OrderProductsEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        orderProducts.forEach(s -> s.setOrder(orderEntity));
        orderRepository.save(orderEntity);
    }

    public OrderStats getOrderStatsByShop(OrderStatsRequest request, Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();
        ShopEntity storedShopEntity = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
        List<OrderEntity> orders = orderRepository.findAllByShopIdAndOrderDateBetween(
                storedShopEntity.getId(),
                request.getFrom().atDay(1).atStartOfDay(),
                request.getTo().atEndOfMonth().atTime(23, 59, 59));
        Map<YearMonth, List<OrderStats.ProductStat>> stats = orderStatsCollector.collect(orders);
        return new OrderStats().setStats(stats);
    }


    @Transactional
    public void deleteOrder(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id (" + orderId + ") not found!"));

        orderEntity.getOrderProducts().stream().map(
                orderProduct -> {
                    ProductEntity product = orderProduct.getProduct();
                    StockEntity stock = product.getStock();
                    stock.addToQuantity(orderProduct.getQuantity());
                    return stock;
                }
        ).forEach(stockRepository::save);

        orderRepository.delete(orderEntity);
    }
}
