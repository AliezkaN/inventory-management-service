package com.nahorniak.inventorymanagementservice.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Role {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
