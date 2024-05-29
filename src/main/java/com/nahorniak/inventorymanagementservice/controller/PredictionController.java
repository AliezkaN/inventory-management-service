package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.service.PredictionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/predictions")
public class PredictionController {
    private final PredictionService service;

    @GetMapping("/predict")
    public PredictionService.PredictionResponse predict(Authentication authentication){
        return service.predict(authentication);
    }
}
