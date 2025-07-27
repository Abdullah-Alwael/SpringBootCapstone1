package com.spring.boot.ecommerce.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "productID must not be empty")
    private String productID;

    @NotEmpty(message = "merchantID must not be empty")
    private String merchantID;

    @NotNull(message = "stock must not be empty")
    @Min(value = 11, message = "stock must not be less than 11 initially")
    private int stock;

}
