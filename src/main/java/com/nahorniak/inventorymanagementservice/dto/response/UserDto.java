package com.nahorniak.inventorymanagementservice.dto.response;

import com.nahorniak.inventorymanagementservice.domain.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean accountLocked;
    private boolean enabled;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
