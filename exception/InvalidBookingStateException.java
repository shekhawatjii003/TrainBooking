package com.example.trainbooking.exception;

/**
 * Thrown when an operation is attempted against a Booking or Payment that
 * is not in a valid state for that operation (e.g. cancelling an already
 * cancelled booking, refunding a payment that never succeeded, or a
 * payment that failed while completing a reservation).
 * <p>
 * Handled globally and mapped to HTTP 409 (CONFLICT).
 */
public class InvalidBookingStateException extends RuntimeException {

    public InvalidBookingStateException(String message) {
        super(message);
    }
}
