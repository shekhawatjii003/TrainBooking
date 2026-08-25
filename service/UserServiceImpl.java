package com.example.trainbooking.service;

import com.example.trainbooking.dto.UserRequest;
import com.example.trainbooking.dto.UserResponse;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.exception.UserAlreadyExistsException;
import com.example.trainbooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponse createUser(UserRequest user) {
        if(repo.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Already exists");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        newUser.setRole("USER");
         User savedUser=repo.save(newUser);
         UserResponse userResponse = new UserResponse();
         userResponse.setEmail(savedUser.getEmail());
         userResponse.setName(savedUser.getName());
         userResponse.setRole(savedUser.getRole());
         userResponse.setId(savedUser.getId());
         return userResponse;
    }
}
