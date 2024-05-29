package com.nahorniak.inventorymanagementservice.repository;

import com.nahorniak.inventorymanagementservice.persistance.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByProductId(Long productId);

    default Map<Long, StockEntity> findByProductIds(Collection<Long> productIds){
        return findByProductIdIn(productIds)
                .stream()
                .filter(x -> x.getProduct() != null)
                .collect(Collectors.toMap(x -> x.getProduct().getId(), x -> x));
    }
    List<StockEntity> findByProductIdIn(Collection<Long> productIds);
}
