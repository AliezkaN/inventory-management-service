package com.nahorniak.inventorymanagementservice.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "shops")
@Accessors(chain = true)
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String contactNumber;
    private String description;
}
