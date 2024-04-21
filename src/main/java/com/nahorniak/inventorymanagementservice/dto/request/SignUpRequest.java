package com.nahorniak.inventorymanagementservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class SignUpRequest {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    private String email;
    @NotEmpty
    @Min(8)
    private String password;
    @Pattern(regexp = "(?i)^(ADMIN|MANAGER|SUPPLIER)$")
    private String role;
}
