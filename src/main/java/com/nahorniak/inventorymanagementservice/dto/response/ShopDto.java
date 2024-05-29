package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;

@Data
public class ShopDto {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private String description;
}
