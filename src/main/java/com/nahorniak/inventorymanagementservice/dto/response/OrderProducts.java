package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProducts {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

}
