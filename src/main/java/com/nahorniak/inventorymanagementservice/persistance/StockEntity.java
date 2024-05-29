package com.nahorniak.inventorymanagementservice.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "stocks")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity = 0;

    public void addToQuantity(Integer quantity) {
        this.setQuantity(getQuantity() + quantity);
    }

    public void extractFromQuantity(Integer quantity) {
        this.setQuantity(getQuantity() - quantity);
    }
}
