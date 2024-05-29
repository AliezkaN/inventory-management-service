package com.nahorniak.inventorymanagementservice.repository;

import com.nahorniak.inventorymanagementservice.persistance.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByShopId(Long shopId);
    List<ProductEntity> findAllByIdIn(Collection<Long> ids);
}
