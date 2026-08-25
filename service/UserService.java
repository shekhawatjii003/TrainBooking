package com.example.trainbooking.service;

import com.example.trainbooking.dto.UserRequest;
import com.example.trainbooking.dto.UserResponse;
import com.example.trainbooking.entity.User;

public interface UserService {

    UserResponse createUser(UserRequest user);
}