package com.nahorniak.inventorymanagementservice.dto.request;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetails> orderDetails;
    @Data
    public static class OrderDetails {
        private Long productId;
        private Integer quantity;
    }
}
