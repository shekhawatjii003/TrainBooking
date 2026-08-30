package com.example.trainbooking.controller;

import com.example.trainbooking.dto.BookingResponse;
import com.example.trainbooking.dto.ReservationBookingRequest;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.mapper.ReservationMapper;
import com.example.trainbooking.service.ReservationBookingService;
import com.example.trainbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationBookingController {

    private final ReservationBookingService reservationBookingService;
    private final ReservationMapper reservationMapper;
    private final UserService userService;

    public ReservationBookingController(
            ReservationBookingService reservationBookingService,
            ReservationMapper reservationMapper,
            UserService userService) {

        this.reservationBookingService = reservationBookingService;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
    }


    // =========================================================
    // BOOK RESERVATION
    // =========================================================

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookReservation(
            @Valid @RequestBody ReservationBookingRequest request) {

        // 1. Get logged-in user from JWT
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        // 2. Get email from JWT
        String email = authentication.getName();

        // 3. Find user
        User user =
                userService.getUserByEmail(email);

        // 4. Convert BookingRequest -> Booking
        Booking booking =
                reservationMapper.convertBookingRequest(
                        request.getBooking()
                );

        // 5. Set logged-in user
        booking.setUser(user);

        // 6. Convert PassengerRequest -> Passenger
        List<Passenger> passengers =
                request.getPassengers()
                        .stream()
                        .map(reservationMapper::convertPassengerRequest)
                        .toList();

        // 7. Book reservation
        Booking savedBooking =
                reservationBookingService.book(
                        booking,
                        passengers
                );

        // 8. Convert Booking -> BookingResponse
        BookingResponse response =
                mapToResponse(savedBooking);

        // 9. Return clean response
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    // =========================================================
    // BOOKING -> BOOKING RESPONSE
    // =========================================================

    private BookingResponse mapToResponse(
            Booking booking) {

        BookingResponse response =
                new BookingResponse();

        // -----------------------------------------------------
        // Booking
        // -----------------------------------------------------

        response.setId(
                booking.getId()
        );

        response.setPnr(
                booking.getPnr()
        );


        // -----------------------------------------------------
        // User
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
        // Train Journey
        // -----------------------------------------------------

        if (booking.getTrainJourney() != null) {

            response.setTrainJourneyId(
                    booking.getTrainJourney().getId()
            );

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