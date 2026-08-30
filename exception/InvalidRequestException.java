package com.example.trainbooking.exception;

/**
 * Thrown when the caller supplied data that fails a business validation
 * rule: a required field is missing, a value is out of range, two fields
 * conflict with each other, or an attempt is made to change a field that
 * must remain immutable (e.g. PNR, train number, station code).
 * <p>
 * Handled globally and mapped to HTTP 400 (BAD_REQUEST).
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
