package com.example.trainbooking.exception;

/**
 * Thrown when a requested resource (Train, Station, Booking, Seat, Payment,
 * Passenger, Coach, TrainJourney, TrainSchedule, TrainStop, User, etc.)
 * could not be found in the system.
 * <p>
 * Handled globally and mapped to HTTP 404 (NOT_FOUND).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
