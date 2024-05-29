package com.nahorniak.inventorymanagementservice.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Table(name = "orders_products")
@Entity
@Getter
@Setter
public class OrderProductsEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private OrderEntity order;
    @ManyToOne
    private ProductEntity product;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price != null ? price : Optional.ofNullable(product)
                .map(ProductEntity::getPrice)
                .map(BigDecimal.valueOf(quantity)::multiply)
                .orElse(BigDecimal.ZERO);
    }
}
