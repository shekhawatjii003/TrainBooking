package com.example.trainbooking.service;

import com.example.trainbooking.dto.PassengerTicketResponse;
import com.example.trainbooking.dto.TicketResponse;
import com.example.trainbooking.entity.*;
import com.example.trainbooking.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final UserService userService;

    public TicketServiceImpl(
            BookingService bookingService,
            PassengerService passengerService,
            UserService userService) {

        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.userService = userService;
    }

    @Override
    public TicketResponse getTicketByPnr(String pnr) {

        // 1. Get logged-in user from JWT
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        // 2. Find logged-in user
        User loggedInUser =
                userService.getUserByEmail(email);

        // 3. Find booking using PNR
        Booking booking =
                bookingService.getBookingByPnr(pnr);

        // 4. Check booking belongs to logged-in user
        if (!booking.getUser().getId()
                .equals(loggedInUser.getId())) {

            throw new UnauthorizedException(
                    "You are not authorized to view this ticket"
            );
        }

        // 5. Get journey
        TrainJourney journey =
                booking.getTrainJourney();

        // 6. Get train
        Train train =
                journey.getTrain();

        // 7. Get passengers
        List<Passenger> passengers =
                passengerService.getPassengerByBooking(
                        booking
                );

        // 8. Convert passengers
        List<PassengerTicketResponse> passengerResponses =
                passengers.stream()
                        .map(this::convertPassenger)
                        .toList();

        // 9. Create response
        TicketResponse response =
                new TicketResponse();

        response.setPnr(booking.getPnr());
        response.setBookingId(booking.getId());
        response.setTrainNumber(train.getTrainNumber());
        response.setTrainName(train.getTrainName());
        response.setJourneyDate(journey.getJourneyDate());

        response.setSource(
                booking.getSourceStation()
                        .getStationCode()
        );

        response.setDestination(
                booking.getDestinationStation()
                        .getStationCode()
        );

        response.setPassengers(passengerResponses);

        response.setTotalFare(
                booking.getTotalFare()
        );

        response.setBookingStatus(
                booking.getBookingStatus().name()
        );

        return response;
    }

    private PassengerTicketResponse convertPassenger(
            Passenger passenger) {

        SeatInventory inventory =
                passenger.getSeatInventory();

        if (inventory == null) {
            throw new RuntimeException(
                    "Seat inventory not found for passenger"
            );
        }

        Seat seat = inventory.getSeat();

        if (seat == null) {
            throw new RuntimeException(
                    "Seat not found for passenger"
            );
        }

        Coach coach = seat.getCoach();

        if (coach == null) {
            throw new RuntimeException(
                    "Coach not found for seat"
            );
        }

        PassengerTicketResponse response =
                new PassengerTicketResponse();

        response.setName(passenger.getName());
        response.setAge(passenger.getAge());
        response.setGender(
                passenger.getGender().name()
        );

        response.setCoachNumber(
                coach.getCoachNumber()
        );

        response.setSeatNumber(
                seat.getSeatNumber()
        );

        response.setSeatType(
                seat.getSeatType().name()
        );

        response.setSeatStatus(
                inventory.getSeatStatus().name()
        );

        return response;
    }
}