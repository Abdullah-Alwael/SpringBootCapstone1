package com.spring.boot.ecommerce.Controller;

import com.spring.boot.ecommerce.Api.ApiResponse;
import com.spring.boot.ecommerce.Model.MerchantStock;
import com.spring.boot.ecommerce.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-stock/")
@RequiredArgsConstructor
public class MerchantStockController {


    private final MerchantStockService merchantStockService; // no need to use the new word, Spring handles it in the container

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        if (merchantStockService.addMerchantStock(merchantStock)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchantStock was added Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
                    ApiResponse("Error, either productID, MerchantID, or both do not exist"));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMerchantStocks() {
        return ResponseEntity.status(HttpStatus.OK).body(merchantStockService.getMerchantStocks());
    }

    @PutMapping("/update/{merchantStockID}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String merchantStockID,
                                                 @Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantStockService.updateMerchantStock(merchantStockID, merchantStock)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchantStock updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
                    ApiResponse("Error, some or all of merchantStockID, productID, MerchantID are not found"));
        }
    }

    @DeleteMapping("/delete/{merchantStockID}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String merchantStockID) {
        if (merchantStockService.deleteMerchantStock(merchantStockID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchantStock deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }
}
