package com.example.trainbooking.service;

import com.example.trainbooking.dto.AuthResponse;
import com.example.trainbooking.dto.LoginRequest;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.exception.InvalidCredentialsException;
import com.example.trainbooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final  PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
       Optional<User> user= userRepository.findByEmail(loginRequest.getEmail());
       if(user.isEmpty()) {
           throw new InvalidCredentialsException("Invalid email or password");
       }
       if(!passwordEncoder.matches(loginRequest.getPassword(),user.get().getPassword())) {
           throw new InvalidCredentialsException("Invalid email or password");
       }
       String token= jwtService.generateToken(loginRequest.getEmail());
       AuthResponse authResponse = new AuthResponse();
       authResponse.setToken(token);
       return authResponse;
    }
}
