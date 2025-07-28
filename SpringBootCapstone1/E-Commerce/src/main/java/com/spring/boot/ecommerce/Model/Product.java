package com.spring.boot.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 4, message = "name must not be less than 4 characters")
    private String name;

    @NotNull(message = "price must not be empty")
    @Positive(message = "price must be a positive number")
    private double price;

    @NotEmpty(message = "categoryID must not be empty")
    private String categoryID; // from the category class

    // TODO Extra user variable:
    private int timesPurchased; // how many times the product was purchased
}
