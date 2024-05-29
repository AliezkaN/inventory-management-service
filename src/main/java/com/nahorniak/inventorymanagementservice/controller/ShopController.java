
package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.request.ShopRequest;
import com.nahorniak.inventorymanagementservice.dto.response.ShopDto;
import com.nahorniak.inventorymanagementservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    public ShopDto getShop(Authentication connectedUser) {
        return shopService.getShopByConnectedUser(connectedUser);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ShopDto update(@RequestBody ShopRequest request, Authentication connectedUser) {
        return shopService.update(request, connectedUser);
    }
}
