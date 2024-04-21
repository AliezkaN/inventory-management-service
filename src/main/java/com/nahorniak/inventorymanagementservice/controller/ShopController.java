
package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.domain.Shop;
import com.nahorniak.inventorymanagementservice.dto.request.ShopRequest;
import com.nahorniak.inventorymanagementservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/")
    public ResponseEntity<List<Shop>> getAll() {
        return ResponseEntity.ok().body(shopService.getAll());
    }


    @GetMapping("/{id}")
    public Shop getById(@PathVariable Long id) {
        return shopService.getById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Shop create(@RequestBody ShopRequest request) {
        return shopService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Shop update(@PathVariable Long id, @RequestBody ShopRequest request) {
        return shopService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        shopService.delete(id);
    }
}
