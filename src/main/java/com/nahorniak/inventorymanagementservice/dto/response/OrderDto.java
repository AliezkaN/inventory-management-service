package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private List<OrderProducts> orderProducts;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
}
