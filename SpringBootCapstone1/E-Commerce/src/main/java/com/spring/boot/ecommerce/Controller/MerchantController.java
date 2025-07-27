package com.spring.boot.ecommerce.Controller;

import com.spring.boot.ecommerce.Api.ApiResponse;
import com.spring.boot.ecommerce.Model.Merchant;
import com.spring.boot.ecommerce.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant/")
@RequiredArgsConstructor
public class MerchantController {


    private final MerchantService merchantService; // no need to use the new word, Spring handles it in the container

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchant was added Successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMerchants() {
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getMerchants());
    }

    @PutMapping("/update/{merchantID}")
    public ResponseEntity<?> updateMerchant(@PathVariable String merchantID,
                                            @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantService.updateMerchant(merchantID, merchant)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchant updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

    @DeleteMapping("/delete/{merchantID}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String merchantID) {
        if (merchantService.deleteMerchant(merchantID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("merchant deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }
}
