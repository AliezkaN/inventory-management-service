package com.nahorniak.inventorymanagementservice.repository;

import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    Optional<ShopEntity> findByManagerId(Long managerId);
}
