package com.nahorniak.inventorymanagementservice.domain;

import lombok.Data;

@Data
public class Shop {
    private Long id;
    private String address;
    private String contactNumber;
    private String description;
}
