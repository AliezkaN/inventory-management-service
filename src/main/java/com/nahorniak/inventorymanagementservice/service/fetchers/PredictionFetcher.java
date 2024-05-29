package com.nahorniak.inventorymanagementservice.service.fetchers;

import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.service.PredictionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PredictionFetcher {

    private final URI url;
    private final RestTemplate restTemplate;

    public PredictionApiResponse fetch(Map<YearMonth, List<OrderStats.ProductStat>> yearMonthToSoldItems) {
        PredictionApiRequest apiRequest = new PredictionApiRequest(yearMonthToSoldItems);
        return restTemplate.postForObject(url, apiRequest, PredictionApiResponse.class);
    }

    @Data
    @AllArgsConstructor
    public static final class PredictionApiRequest {
        private Map<YearMonth, List<OrderStats.ProductStat>> stats;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PredictionApiResponse {
        private Map<Long, Integer> prediction;
    }

}
