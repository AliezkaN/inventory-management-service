package com.nahorniak.inventorymanagementservice.repository;

import com.nahorniak.inventorymanagementservice.persistance.OrderEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByShopId(@NotNull Long shopId);
    List<OrderEntity> findAllByShopIdAndOrderDateBetween(@NotNull Long shopId,
                                                         @NotNull LocalDateTime from,
                                                         @NotNull LocalDateTime to);
}
