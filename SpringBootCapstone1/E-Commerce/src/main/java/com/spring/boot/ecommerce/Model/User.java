package com.spring.boot.ecommerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "userName must not be empty")
    @Size(min = 6, message = "userName must not be less than 6 characters")
    private String userName;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 7, message = "password must not be less than 7 characters")
    @Pattern(regexp = "^([A-Za-z\\d])+$") // only characters and numbers, no special characters will be allowed
    private String password;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be a valid email")
    private String email;

    @NotEmpty(message = "role must not be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "role must be either Admin or Customer")
    private String role;

    @NotNull(message = "balance must not be empty")
    @Positive(message = "balance must be a positive number")
    private double balance;

    // TODO Extra variables:
    private String orderHistory;
}
