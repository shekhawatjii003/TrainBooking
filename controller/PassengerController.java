package com.example.trainbooking.controller;

import com.example.trainbooking.dto.PassengerResponse;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.service.BookingService;
import com.example.trainbooking.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    // =========================================================
    // GET ALL PASSENGERS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getPassengers() {

        List<Passenger> passengers =
                passengerService.getPassengers();

        List<PassengerResponse> response =
                passengers.stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET PASSENGER BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassenger(
            @PathVariable Long id) {

        Passenger passenger =
                passengerService.getPassenger(id);

        return ResponseEntity.ok(
                mapToResponse(passenger)
        );
    }


    // =========================================================
    // GET PASSENGERS BY BOOKING
    // =========================================================

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PassengerResponse>>
    getPassengersByBooking(
            @PathVariable Long bookingId) {

        Booking booking =
                bookingService.getBookingById(bookingId);

        List<Passenger> passengers =
                passengerService.getPassengerByBooking(
                        booking
                );

        List<PassengerResponse> response =
                passengers.stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // CREATE PASSENGER
    // =========================================================

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(
            @RequestBody Passenger passenger) {

        Passenger savedPassenger =
                passengerService.createPassenger(
                        passenger
                );

        return ResponseEntity.ok(
                mapToResponse(savedPassenger)
        );
    }


    // =========================================================
    // UPDATE PASSENGER
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @PathVariable Long id,
            @RequestBody Passenger passenger) {

        Passenger updatedPassenger =
                passengerService.updatePassenger(
                        id,
                        passenger
                );

        return ResponseEntity.ok(
                mapToResponse(updatedPassenger)
        );
    }


    // =========================================================
    // DELETE / DEACTIVATE PASSENGER
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> deletePassenger(
            @PathVariable Long id) {

        Passenger deletedPassenger =
                passengerService.deletePassenger(id);

        return ResponseEntity.ok(
                mapToResponse(deletedPassenger)
        );
    }


    // =========================================================
    // PASSENGER -> RESPONSE DTO
    // =========================================================

    private PassengerResponse mapToResponse(
            Passenger passenger) {

        PassengerResponse response =
                new PassengerResponse();

        // Passenger
        response.setId(
                passenger.getId()
        );

        response.setName(
                passenger.getName()
        );

        response.setAge(
                passenger.getAge()
        );

        response.setGender(
                passenger.getGender()
        );

        response.setActive(
                passenger.getActive()
        );


        // Booking
        if (passenger.getBooking() != null) {

            response.setBookingId(
                    passenger.getBooking().getId()
            );

            response.setPnr(
                    passenger.getBooking().getPnr()
            );
        }


        // Seat Inventory
        if (passenger.getSeatInventory() != null) {

            response.setSeatInventoryId(
                    passenger.getSeatInventory().getId()
            );

            // Seat
            if (passenger.getSeatInventory()
                    .getSeat() != null) {

                response.setSeatNumber(
                        passenger.getSeatInventory()
                                .getSeat()
                                .getSeatNumber()
                );

                response.setSeatType(
                        passenger.getSeatInventory()
                                .getSeat()
                                .getSeatType()
                                .name()
                );

                // Coach
                if (passenger.getSeatInventory()
                        .getSeat()
                        .getCoach() != null) {

                    response.setCoachNumber(
                            passenger.getSeatInventory()
                                    .getSeat()
                                    .getCoach()
                                    .getCoachNumber()
                    );
                }
            }
        }

        return response;
    }
}