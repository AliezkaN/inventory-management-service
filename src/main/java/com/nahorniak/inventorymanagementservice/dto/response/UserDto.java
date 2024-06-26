package com.nahorniak.inventorymanagementservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private boolean accountLocked;
    private boolean enabled;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
