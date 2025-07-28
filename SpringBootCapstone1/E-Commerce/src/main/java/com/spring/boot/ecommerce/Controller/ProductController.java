package com.spring.boot.ecommerce.Controller;

import com.spring.boot.ecommerce.Api.ApiResponse;
import com.spring.boot.ecommerce.Model.Product;
import com.spring.boot.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService; // no need to use the new word, Spring handles it in the container

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        if (productService.addProduct(product)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product was added Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error category does not exist"));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @PutMapping("/update/{productID}")
    public ResponseEntity<?> updateProduct(@PathVariable String productID,
                                           @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (productService.updateProduct(productID, product)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
                    ApiResponse("Error, either productID or categoryID not found"));
        }
    }

    @DeleteMapping("/delete/{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productID) {
        if (productService.deleteProduct(productID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("product deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

    // TODO Extra end points:
    // based on the number of purchase, display a list of best selling items
    @GetMapping("/best-selling")
    public ResponseEntity<?> listBestSellingProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.listBestSellingProducts());
    }

    // for advertisement purposes, add a score value to product
    @PutMapping("/score/{userID}/{productID}/{newScore}")
    public ResponseEntity<?> updateProductScore(@PathVariable String userID,
                                                @PathVariable String productID,
                                                @PathVariable double newScore){

        if (productService.updateProductScore(userID, productID, newScore)){
            return ResponseEntity.status(HttpStatus.OK).body(new
                    ApiResponse("Advertisement score was updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse("Error, either user is not an Admin or the product and/or user do not exist"));
        }

    }

    // display advertisements based on the Admin set scores. display generally in the website
    @GetMapping("/advertisement")
    public ResponseEntity<?> displayAdvertisement(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.displayAdvertisement());
    }

    // get similar products by category to display in the product view page sorted by score
    @GetMapping("/similar-products/{productID}")
    public ResponseEntity<?> displaySimilarProducts(@PathVariable String productID){
        return ResponseEntity.status(HttpStatus.OK).body(productService.displaySimilarProducts(productID));
    }

}
