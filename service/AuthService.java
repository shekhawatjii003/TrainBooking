package com.example.trainbooking.service;

import com.example.trainbooking.dto.AuthResponse;
import com.example.trainbooking.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}
