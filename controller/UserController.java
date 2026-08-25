package com.example.trainbooking.controller;

import com.example.trainbooking.dto.UserRequest;
import com.example.trainbooking.dto.UserResponse;
import com.example.trainbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService impl;
    public UserController(UserService impl) {
        this.impl = impl;
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user){
        UserResponse response=impl.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
