package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class OrderStats {
    private Map<YearMonth, List<ProductStat>> stats;

    @Data
    @Accessors(chain = true)
    public static class ProductStat {
        private Long productId;
        private String productName;
        private Integer quantitySold;
    }
}
