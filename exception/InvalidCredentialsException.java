package com.example.trainbooking.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
