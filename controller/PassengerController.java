package com.example.trainbooking.controller;

import com.example.trainbooking.dto.PassengerRequest;
import com.example.trainbooking.dto.PassengerResponse;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.service.BookingService;
import com.example.trainbooking.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final BookingService bookingService;

    public PassengerController(
            PassengerService passengerService,
            BookingService bookingService) {

        this.passengerService = passengerService;
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassenger(
            @PathVariable Long id) {

        Passenger passenger = passengerService.getPassenger(id);

        return ResponseEntity.ok(PassengerResponse.from(passenger));
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getPassengers() {

        List<PassengerResponse> passengers = passengerService.getPassengers()
                .stream()
                .map(PassengerResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PassengerResponse>> getPassengersByBooking(
            @PathVariable Long bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);

        List<PassengerResponse> passengers = passengerService
                .getPassengerByBooking(booking)
                .stream()
                .map(PassengerResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(passengers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @PathVariable Long id,
            @RequestBody PassengerRequest request) {

        // Booking cannot change on update, so keep the existing one
        Passenger existing = passengerService.getPassenger(id);

        Passenger updated = new Passenger();
        updated.setBooking(existing.getBooking());
        updated.setName(request.getName());
        updated.setAge(request.getAge());
        updated.setGender(request.getGender());
        updated.setSeatInventory(existing.getSeatInventory());
        updated.setActive(existing.getActive());

        Passenger saved = passengerService.updatePassenger(id, updated);

        return ResponseEntity.ok(PassengerResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> deletePassenger(
            @PathVariable Long id) {

        Passenger passenger = passengerService.deletePassenger(id);

        return ResponseEntity.ok(PassengerResponse.from(passenger));
    }
}
