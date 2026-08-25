package com.example.trainbooking.service;

import com.example.trainbooking.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationBookingServiceImpl
        implements ReservationBookingService {

    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    public ReservationBookingServiceImpl(
            BookingService bookingService,
            PassengerService passengerService,
            ReservationService reservationService,
            PaymentService paymentService) {

        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
    }

    private void validateBooking(Booking booking) {

        if (booking == null) {
            throw new RuntimeException("Booking is required");
        }

        if (booking.getUser() == null) {
            throw new RuntimeException("User is required");
        }

        if (booking.getTrainJourney() == null) {
            throw new RuntimeException("Train journey is required");
        }

        if (!booking.getTrainJourney().getActive()) {
            throw new RuntimeException("Train journey is not active");
        }

        if (booking.getSourceStation() == null) {
            throw new RuntimeException("Source station is required");
        }

        if (booking.getDestinationStation() == null) {
            throw new RuntimeException("Destination station is required");
        }

        if (booking.getSourceStation()
                .equals(booking.getDestinationStation())) {

            throw new RuntimeException(
                    "Source and destination cannot be same"
            );
        }
    }

    private void validatePassenger(Passenger passenger) {

        if (passenger == null) {
            throw new RuntimeException("Passenger is required");
        }

        if (passenger.getName() == null ||
                passenger.getName().trim().isEmpty()) {

            throw new RuntimeException("Passenger name is required");
        }

        if (passenger.getAge() == null ||
                passenger.getAge() <= 0) {

            throw new RuntimeException(
                    "Passenger age must be greater than 0"
            );
        }

        if (passenger.getGender() == null) {
            throw new RuntimeException("Passenger gender is required");
        }

        if (passenger.getSeatInventory() == null) {
            throw new RuntimeException("Seat is required");
        }

        if (!passenger.getSeatInventory().getActive()) {
            throw new RuntimeException(
                    "Seat inventory is not active"
            );
        }

        if (passenger.getSeatInventory().getSeat() == null) {
            throw new RuntimeException("Seat is required");
        }

        if (passenger.getSeatInventory().getTrainJourney() == null) {
            throw new RuntimeException(
                    "Train journey is required for seat"
            );
        }
    }

    @Override
    @Transactional
    public Booking book(
            Booking booking,
            List<Passenger> passengers) {

        // 1. Validate booking
        validateBooking(booking);

        // 2. Validate passengers
        if (passengers == null || passengers.isEmpty()) {
            throw new RuntimeException(
                    "At least one passenger is required"
            );
        }

        for (Passenger passenger : passengers) {
            validatePassenger(passenger);
        }

        // 3. Create booking
        Booking savedBooking =
                bookingService.createBooking(booking);

        // 4. Keep track of successfully reserved passengers
        List<Passenger> reservedPassengers =
                new ArrayList<>();

        try {

            // 5. Reserve seats and create passengers
            for (Passenger passenger : passengers) {

                passenger.setBooking(savedBooking);

                SeatInventory inventory =
                        passenger.getSeatInventory();

                TrainJourney journey =
                        inventory.getTrainJourney();

                Seat seat =
                        inventory.getSeat();

                // Lock seat
                reservationService.reserveSeat(
                        journey,
                        seat,
                        savedBooking
                );

                // Create passenger
                passengerService.createPassenger(
                        passenger
                );

                // Remember successfully reserved passenger
                reservedPassengers.add(passenger);
            }

            // 6. Generate unique transaction ID
            String transactionId =
                    "TXN-" + UUID.randomUUID();

            // 7. Create ONE payment for the booking
            Payment payment = new Payment();

            payment.setBooking(savedBooking);
            payment.setAmount(savedBooking.getTotalFare());
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaymentMethod(PaymentMethod.UPI);
            payment.setTransactionId(transactionId);
            payment.setPaymentDateTime(LocalDateTime.now());
            // 8. Save payment
            Payment savedPayment =
                    paymentService.createPayment(payment);

            // 9. Check payment result
            if (savedPayment.getPaymentStatus()
                    != PaymentStatus.SUCCESS) {

                throw new RuntimeException(
                        "Payment failed"
                );
            }

            // 10. Confirm ALL reserved seats
            for (Passenger passenger : reservedPassengers) {

                SeatInventory inventory =
                        passenger.getSeatInventory();

                TrainJourney journey =
                        inventory.getTrainJourney();

                Seat seat =
                        inventory.getSeat();

                reservationService.confirmReservation(
                        journey,
                        seat,
                        savedBooking
                );
            }

            // 11. Update booking status
            savedBooking.setBookingStatus(
                    BookingStatus.CONFIRMED
            );

            // 12. Return confirmed booking
            return bookingService.updateBooking(
                    savedBooking.getId(),
                    savedBooking
            );

        } catch (RuntimeException e) {

            // 13. Release all locked seats
            for (Passenger passenger : reservedPassengers) {

                SeatInventory inventory =
                        passenger.getSeatInventory();

                TrainJourney journey =
                        inventory.getTrainJourney();

                Seat seat =
                        inventory.getSeat();

                try {
                    reservationService.releaseSeat(
                            journey,
                            seat,
                            savedBooking
                    );
                } catch (RuntimeException ignored) {
                    // Do not hide the original exception
                }
            }

            throw e;
        }
    }
}