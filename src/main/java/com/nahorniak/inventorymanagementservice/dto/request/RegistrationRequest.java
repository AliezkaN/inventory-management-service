package com.nahorniak.inventorymanagementservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegistrationRequest {
    @NotBlank(message = "'firstname' must be not blank")
    private String firstname;
    @NotBlank(message = "'lastname' must be not blank")
    private String lastname;
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8, message = "'password' should have at least 8 symbols")
    private String password;
    @Pattern(regexp = "(?i)^(MANAGER)$", message = "Invalid role!")
    private String role;

    @NotBlank(message = "'shopName' must be not blank")
    private String shopName;
    @NotBlank(message = "'shopAddress' must be not blank")
    private String shopAddress;
    @NotBlank(message = "'shopContactNumber' must be not blank")
    private String shopContactNumber;
    private String shopDescription;
}
