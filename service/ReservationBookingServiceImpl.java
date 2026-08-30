package com.example.trainbooking.service;

import com.example.trainbooking.entity.*;
import com.example.trainbooking.repository.SeatInventoryRepository;
import com.example.trainbooking.repository.TrainJourneyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.SeatNotAvailableException;
import com.example.trainbooking.exception.InvalidBookingStateException;
import com.example.trainbooking.exception.InvalidRequestException;

@Service
public class ReservationBookingServiceImpl
        implements ReservationBookingService {

    private final BookingService bookingService;
    private final PassengerService passengerService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    private final TrainJourneyRepository trainJourneyRepository;
    private final SeatInventoryRepository seatInventoryRepository;

    public ReservationBookingServiceImpl(
            BookingService bookingService,
            PassengerService passengerService,
            ReservationService reservationService,
            PaymentService paymentService,
            TrainJourneyRepository trainJourneyRepository,
            SeatInventoryRepository seatInventoryRepository) {

        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
        this.trainJourneyRepository = trainJourneyRepository;
        this.seatInventoryRepository = seatInventoryRepository;
    }

    // =========================================================
    // LOAD ACTUAL ENTITIES FROM DATABASE
    // =========================================================

    private void loadActualEntities(
            Booking booking,
            List<Passenger> passengers) {

        // Load actual TrainJourney from database
        Long journeyId =
                booking.getTrainJourney().getId();

        TrainJourney journey =
                trainJourneyRepository.findById(journeyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Train Journey not found"
                                ));

        // Replace partial object with actual database entity
        booking.setTrainJourney(journey);


        // Load actual SeatInventory objects
        for (Passenger passenger : passengers) {

            Long inventoryId =
                    passenger.getSeatInventory().getId();

            SeatInventory inventory =
                    seatInventoryRepository.findById(inventoryId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Seat Inventory not found"
                                    ));

            // Replace partial object with actual entity
            passenger.setSeatInventory(inventory);
        }
    }


    // =========================================================
    // VALIDATE BOOKING
    // =========================================================

    private void validateBooking(Booking booking) {

        if (booking == null) {
            throw new InvalidRequestException(
                    "Booking is required"
            );
        }

        if (booking.getUser() == null) {
            throw new InvalidRequestException(
                    "User is required"
            );
        }

        if (booking.getTrainJourney() == null) {
            throw new InvalidRequestException(
                    "Train journey is required"
            );
        }

        // Boolean.TRUE.equals() prevents NullPointerException
        if (!Boolean.TRUE.equals(
                booking.getTrainJourney().getActive())) {

            throw new InvalidRequestException(
                    "Train journey is not active"
            );
        }

        if (booking.getSourceStation() == null) {
            throw new InvalidRequestException(
                    "Source station is required"
            );
        }

        if (booking.getDestinationStation() == null) {
            throw new InvalidRequestException(
                    "Destination station is required"
            );
        }

        if (booking.getSourceStation()
                .equals(booking.getDestinationStation())) {

            throw new InvalidRequestException(
                    "Source and destination cannot be same"
            );
        }
    }


    // =========================================================
    // VALIDATE PASSENGER
    // =========================================================

    private void validatePassenger(Passenger passenger) {

        if (passenger == null) {
            throw new InvalidRequestException(
                    "Passenger is required"
            );
        }

        if (passenger.getName() == null ||
                passenger.getName().trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Passenger name is required"
            );
        }

        if (passenger.getAge() == null ||
                passenger.getAge() <= 0) {

            throw new InvalidRequestException(
                    "Passenger age must be greater than 0"
            );
        }

        if (passenger.getGender() == null) {

            throw new InvalidRequestException(
                    "Passenger gender is required"
            );
        }

        if (passenger.getSeatInventory() == null) {

            throw new InvalidRequestException(
                    "Seat is required"
            );
        }

        // Boolean.TRUE.equals() prevents NullPointerException
        if (!Boolean.TRUE.equals(
                passenger.getSeatInventory().getActive())) {

            throw new SeatNotAvailableException(
                    "Seat inventory is not active"
            );
        }

        if (passenger.getSeatInventory().getSeat() == null) {

            throw new InvalidRequestException(
                    "Seat is required"
            );
        }

        if (passenger.getSeatInventory()
                .getTrainJourney() == null) {

            throw new InvalidRequestException(
                    "Train journey is required for seat"
            );
        }
    }


    // =========================================================
    // BOOK RESERVATION
    // =========================================================

    @Override
    @Transactional
    public Booking book(
            Booking booking,
            List<Passenger> passengers) {

        // -----------------------------------------------------
        // 1. Basic passenger check
        // -----------------------------------------------------

        if (passengers == null ||
                passengers.isEmpty()) {

            throw new InvalidRequestException(
                    "At least one passenger is required"
            );
        }


        // -----------------------------------------------------
        // 2. Load actual entities from database
        // -----------------------------------------------------

        loadActualEntities(
                booking,
                passengers
        );


        // -----------------------------------------------------
        // 3. Validate booking
        // -----------------------------------------------------

        validateBooking(booking);


        // -----------------------------------------------------
        // 4. Validate passengers
        // -----------------------------------------------------

        for (Passenger passenger : passengers) {

            validatePassenger(passenger);
        }


        // -----------------------------------------------------
        // 5. Create booking
        // -----------------------------------------------------

        Booking savedBooking =
                bookingService.createBooking(booking);


        // -----------------------------------------------------
        // 6. Keep track of successfully reserved passengers
        // -----------------------------------------------------

        List<Passenger> reservedPassengers =
                new ArrayList<>();


        try {

            // =================================================
            // 7. Reserve seats and create passengers
            // =================================================

            for (Passenger passenger : passengers) {

                passenger.setBooking(savedBooking);

                SeatInventory inventory =
                        passenger.getSeatInventory();

                TrainJourney journey =
                        inventory.getTrainJourney();

                Seat seat =
                        inventory.getSeat();


                // ---------------------------------------------
                // Lock / reserve seat
                // ---------------------------------------------

                reservationService.reserveSeat(
                        journey,
                        seat,
                        savedBooking
                );


                // ---------------------------------------------
                // Create passenger
                // ---------------------------------------------

                passengerService.createPassenger(
                        passenger
                );


                // ---------------------------------------------
                // Remember successfully reserved passenger
                // ---------------------------------------------

                reservedPassengers.add(passenger);
            }


            // =================================================
            // 8. Generate transaction ID
            // =================================================

            String transactionId =
                    "TXN-" + UUID.randomUUID();


            // =================================================
            // 9. Create payment
            // =================================================

            Payment payment =
                    new Payment();

            payment.setBooking(savedBooking);

            payment.setAmount(
                    savedBooking.getTotalFare()
            );

            payment.setPaymentStatus(
                    PaymentStatus.SUCCESS
            );

            payment.setPaymentMethod(
                    PaymentMethod.UPI
            );

            payment.setTransactionId(
                    transactionId
            );

            payment.setPaymentDateTime(
                    LocalDateTime.now()
            );


            // =================================================
            // 10. Save payment
            // =================================================

            Payment savedPayment =
                    paymentService.createPayment(
                            payment
                    );


            // =================================================
            // 11. Check payment
            // =================================================

            if (savedPayment.getPaymentStatus()
                    != PaymentStatus.SUCCESS) {

                throw new InvalidBookingStateException(
                        "Payment failed"
                );
            }


            // =================================================
            // 12. Confirm all reserved seats
            // =================================================

            for (Passenger passenger :
                    reservedPassengers) {

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


            // =================================================
            // 13. Update booking status
            // =================================================

            savedBooking.setBookingStatus(
                    BookingStatus.CONFIRMED
            );


            // =================================================
            // 14. Save final booking
            // =================================================

            return bookingService.updateBooking(
                    savedBooking.getId(),
                    savedBooking
            );


        } catch (RuntimeException e) {

            // =================================================
            // 15. Release all locked seats
            // =================================================

            for (Passenger passenger :
                    reservedPassengers) {

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

                    // Do not hide original exception
                }
            }


            // =================================================
            // 16. Re-throw original exception
            // =================================================

            throw e;
        }
    }
}