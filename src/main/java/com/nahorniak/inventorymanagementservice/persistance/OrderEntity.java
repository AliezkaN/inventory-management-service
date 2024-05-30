package com.nahorniak.inventorymanagementservice.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private ShopEntity shop;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderProductsEntity> orderProducts;
    private BigDecimal totalAmount;
}
