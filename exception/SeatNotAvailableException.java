package com.example.trainbooking.exception;

/**
 * Thrown when a seat / seat inventory record is not in the state required
 * to complete a reservation operation (e.g. the seat is inactive, already
 * booked, not locked, or the locking passenger does not match).
 * <p>
 * Handled globally and mapped to HTTP 409 (CONFLICT).
 */
public class SeatNotAvailableException extends RuntimeException {

    public SeatNotAvailableException(String message) {
        super(message);
    }
}
