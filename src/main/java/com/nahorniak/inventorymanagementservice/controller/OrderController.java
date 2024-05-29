package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.request.OrderRequest;
import com.nahorniak.inventorymanagementservice.dto.request.OrderStatsRequest;
import com.nahorniak.inventorymanagementservice.dto.response.OrderDto;
import com.nahorniak.inventorymanagementservice.dto.response.OrderStats;
import com.nahorniak.inventorymanagementservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;


    @GetMapping
    public List<OrderDto> getAll(Authentication authentication) {
        return service.getAllByUser(authentication);
    }


    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return service.getOrderById(orderId);
    }

    @PostMapping("/stats")
    public OrderStats getOrderStatsByShop(@RequestBody OrderStatsRequest request, Authentication authentication) {
        return service.getOrderStatsByShop(request, authentication);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderRequest request, Authentication authentication) {
        service.createOrder(request, authentication);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
    }

}
