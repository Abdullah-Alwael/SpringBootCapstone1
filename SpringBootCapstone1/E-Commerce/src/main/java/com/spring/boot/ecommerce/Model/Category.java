package com.spring.boot.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "id must not be empty")
    private String id;
    @NotEmpty(message = "name must not be empty")
    @Size(min = 4, message = "name must not be less than 4 characters")
    private String name;
}
