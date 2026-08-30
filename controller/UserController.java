package com.example.trainbooking.controller;

import com.example.trainbooking.dto.UserRequest;
import com.example.trainbooking.dto.UserResponse;
import com.example.trainbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRequest request) {

        UserResponse response =
                userService.createUser(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
}