package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.domain.Shop;
import com.nahorniak.inventorymanagementservice.dto.request.ShopRequest;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final SelmaMapper selmaMapper;

    public List<Shop> getAll() {
        return shopRepository.findAll()
                .stream()
                .map(selmaMapper::toDomain)
                .toList();
    }

    public Shop getById(@NonNull Long id) {
        return shopRepository.findById(id)
                .map(selmaMapper::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Shop with id " + id + " not found!"));
    }

    public Shop create(ShopRequest request) {
        ShopEntity shopEntity = new ShopEntity()
                .setAddress(request.getAddress())
                .setContactNumber(request.getContactNumber())
                .setDescription(request.getDescription());
        ShopEntity storedEntity = shopRepository.save(shopEntity);
        return selmaMapper.toDomain(storedEntity);
    }

    public Shop update(Long id, ShopRequest request) {
        ShopEntity shopEntity = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop with id " + id + " not found!"));
        if (request.getAddress() != null) {
            shopEntity.setAddress(request.getAddress());
        }
        if (request.getContactNumber() != null) {
            shopEntity.setContactNumber(request.getContactNumber());
        }
        if (request.getDescription() != null) {
            shopEntity.setDescription(request.getDescription());
        }
        ShopEntity storedEntity = shopRepository.save(shopEntity);
        return selmaMapper.toDomain(storedEntity);
    }

    public void delete(Long id) {
        ShopEntity shopEntity = shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop with id " + id + " not found!"));
        shopRepository.delete(shopEntity);
    }
}
