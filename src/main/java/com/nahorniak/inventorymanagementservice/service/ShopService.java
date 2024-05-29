package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.request.ShopRequest;
import com.nahorniak.inventorymanagementservice.dto.response.ShopDto;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    private final SelmaMapper selmaMapper;

    public ShopDto getShopByConnectedUser(Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        return shopRepository.findByManagerId(user.getId())
                .map(selmaMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
    }

    public ShopDto update(ShopRequest request, Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
        ShopEntity shopEntity = shopRepository.findByManagerId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop by connected user with id {" + user.getId() + "} not found!"));
        Optional.ofNullable(request.getAddress()).ifPresent(shopEntity::setAddress);
        Optional.ofNullable(request.getContactNumber()).ifPresent(shopEntity::setContactNumber);
        Optional.ofNullable(request.getDescription()).ifPresent(shopEntity::setDescription);
        ShopEntity storedEntity = shopRepository.save(shopEntity);
        return selmaMapper.toDto(storedEntity);
    }
}
