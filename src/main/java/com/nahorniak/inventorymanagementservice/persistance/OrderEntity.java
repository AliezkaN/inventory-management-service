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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_product_id")
    )
    private List<OrderProductsEntity> orderProducts;
    private BigDecimal totalAmount;
}
