package com.example.trainbooking.exception;

/**
 * Thrown when an operation would create a resource that violates a
 * uniqueness rule (e.g. a train number, station code, seat/coach
 * combination, or a train journey that already exists).
 * <p>
 * Handled globally and mapped to HTTP 409 (CONFLICT).
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
