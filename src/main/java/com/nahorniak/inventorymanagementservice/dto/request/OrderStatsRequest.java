package com.nahorniak.inventorymanagementservice.dto.request;

import lombok.Data;

import java.time.YearMonth;

@Data
public class OrderStatsRequest {
    private YearMonth from;
    private YearMonth to;
}
