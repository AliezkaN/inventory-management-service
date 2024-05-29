package com.nahorniak.inventorymanagementservice.utils;

import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.persistance.OrderEntity;
import com.nahorniak.inventorymanagementservice.persistance.OrderProductsEntity;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderStatsCollector implements StatsCollector<Map<YearMonth, List<OrderStats.ProductStat>>, OrderEntity> {

    @Override
    public Map<YearMonth, List<OrderStats.ProductStat>> collect(Collection<OrderEntity> entities) {
        return entities
                .stream()
                .collect(Collectors.groupingBy(
                        order -> YearMonth.from(order.getOrderDate()),
                        Collectors.mapping(OrderEntity::getOrderProducts, Collectors.collectingAndThen(
                                Collectors.toList(),
                                groupedOrderProducts -> groupedOrderProducts.stream()
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.groupingBy(
                                                OrderProductsEntity::getProduct,
                                                Collectors.collectingAndThen(
                                                        Collectors.toList(),
                                                        orderProducts ->
                                                                orderProducts.stream()
                                                                        .map(OrderProductsEntity::getQuantity)
                                                                        .reduce(0, Integer::sum)
                                                )))
                                        .entrySet()
                                        .stream()
                                        .map(e -> new OrderStats.ProductStat()
                                                .setProductId(e.getKey().getId())
                                                .setProductName(e.getKey().getName())
                                                .setQuantitySold(e.getValue()))
                                        .toList()
                        ))
                ));
    }
}
