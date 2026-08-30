package com.example.trainbooking.controller;

import com.example.trainbooking.dto.BookingResponse;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.exception.UnauthorizedException;
import com.example.trainbooking.service.BookingService;
import com.example.trainbooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(
            BookingService bookingService,
            UserService userService) {

        this.bookingService = bookingService;
        this.userService = userService;
    }

    // =========================================================
    // GET MY BOOKINGS
    // =========================================================

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        // Get logged-in user from JWT
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        // Find user
        User user =
                userService.getUserByEmail(email);

        // Get user's bookings
        List<Booking> bookings =
                bookingService.getBookingsByUser(user);

        // Convert Booking -> BookingResponse
        List<BookingResponse> response =
                bookings.stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // CANCEL MY BOOKING
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id) {

        // Get logged-in user from JWT
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        // Find logged-in user
        User user =
                userService.getUserByEmail(email);

        // Find booking
        Booking booking =
                bookingService.getBookingById(id);

        // Check ownership
        if (!booking.getUser().getId()
                .equals(user.getId())) {

            throw new UnauthorizedException(
                    "You are not authorized to cancel this booking"
            );
        }

        // Cancel booking
        Booking cancelledBooking =
                bookingService.cancelBooking(id);

        // Convert to DTO
        BookingResponse response =
                mapToResponse(cancelledBooking);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // BOOKING -> BOOKING RESPONSE
    // =========================================================

    private BookingResponse mapToResponse(
            Booking booking) {

        BookingResponse response =
                new BookingResponse();

        // -----------------------------------------------------
        // Booking information
        // -----------------------------------------------------

        response.setId(
                booking.getId()
        );

        response.setPnr(
                booking.getPnr()
        );


        // -----------------------------------------------------
        // User information
        // -----------------------------------------------------

        if (booking.getUser() != null) {

            response.setUserId(
                    booking.getUser().getId()
            );

            response.setUserName(
                    booking.getUser().getName()
            );

            response.setUserEmail(
                    booking.getUser().getEmail()
            );

            response.setUserRole(
                    booking.getUser().getRole()
            );
        }


        // -----------------------------------------------------
        // Train Journey information
        // -----------------------------------------------------

        if (booking.getTrainJourney() != null) {

            response.setTrainJourneyId(
                    booking.getTrainJourney().getId()
            );

            // Train information
            if (booking.getTrainJourney().getTrain() != null) {

                response.setTrainNumber(
                        booking.getTrainJourney()
                                .getTrain()
                                .getTrainNumber()
                );

                response.setTrainName(
                        booking.getTrainJourney()
                                .getTrain()
                                .getTrainName()
                );
            }
        }


        // -----------------------------------------------------
        // Source Station
        // -----------------------------------------------------

        if (booking.getSourceStation() != null) {

            response.setSourceStation(
                    booking.getSourceStation()
                            .getStationCode()
            );
        }


        // -----------------------------------------------------
        // Destination Station
        // -----------------------------------------------------

        if (booking.getDestinationStation() != null) {

            response.setDestinationStation(
                    booking.getDestinationStation()
                            .getStationCode()
            );
        }


        // -----------------------------------------------------
        // Booking details
        // -----------------------------------------------------

        response.setBookingDateTime(
                booking.getBookingDateTime()
        );

        response.setBookingStatus(
                booking.getBookingStatus()
        );

        response.setTotalFare(
                booking.getTotalFare()
        );


        return response;
    }
}