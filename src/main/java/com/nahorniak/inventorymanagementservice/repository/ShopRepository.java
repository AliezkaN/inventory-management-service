package com.nahorniak.inventorymanagementservice.repository;

import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
}
