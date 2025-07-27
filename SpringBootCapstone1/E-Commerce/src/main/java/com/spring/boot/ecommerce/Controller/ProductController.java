package com.spring.boot.ecommerce.Controller;

import com.spring.boot.ecommerce.Api.ApiResponse;
import com.spring.boot.ecommerce.Model.Product;
import com.spring.boot.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService; // no need to use the new word, Spring handles it in the container

    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product was added Successfully"));
    }

    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    public ResponseEntity<?> updateProduct(@PathVariable String productID,
                                           @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (productService.updateProduct(productID, product)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

    public ResponseEntity<?> deleteProduct(@PathVariable String productID) {
        if (productService.deleteProduct(productID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

}
