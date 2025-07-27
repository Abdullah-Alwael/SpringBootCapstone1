package com.spring.boot.ecommerce.Controller;

import com.spring.boot.ecommerce.Api.ApiResponse;
import com.spring.boot.ecommerce.Model.User;
import com.spring.boot.ecommerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService; // no need to use the new word, Spring handles it in the container

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user was added Successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PutMapping("/update/{userID}")
    public ResponseEntity<?> updateUser(@PathVariable String userID,
                                        @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (userService.updateUser(userID, user)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user updated Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<?> deleteUser(@PathVariable String userID) {
        if (userService.deleteUser(userID)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error, not found"));
        }
    }

}
