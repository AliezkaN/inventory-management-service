package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;
    private Long shopId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
