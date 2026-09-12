package com.example.trainbooking.service;

import com.example.trainbooking.entity.*;
import com.example.trainbooking.exception.InvalidRequestException;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.repository.BookingRepository;
import com.example.trainbooking.repository.PassengerRepository;
import com.example.trainbooking.repository.SeatInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final SeatInventoryRepository seatInventoryRepository;
    private final PassengerRepository passengerRepository;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            PassengerService passengerService,
            SeatInventoryRepository seatInventoryRepository,
            PassengerRepository passengerRepository) {

        this.bookingRepository = bookingRepository;
        this.passengerService = passengerService;
        this.seatInventoryRepository = seatInventoryRepository;
        this.passengerRepository = passengerRepository;
    }

    // =========================================================
    // CREATE BOOKING
    // =========================================================

    @Override
    public Booking createBooking(Booking booking) {

        // 1. Validate booking
        if (booking == null) {
            throw new InvalidRequestException("Booking is required");
        }

        // 2. Validate User
        if (booking.getUser() == null) {
            throw new InvalidRequestException("User is required");
        }

        // 3. Validate Train Journey
        if (booking.getTrainJourney() == null) {
            throw new InvalidRequestException(
                    "Train Journey is required"
            );
        }

        // Boolean.TRUE.equals() prevents NullPointerException
        if (!Boolean.TRUE.equals(
                booking.getTrainJourney().getActive())) {

            throw new InvalidRequestException(
                    "Train Journey is not active"
            );
        }

        // 4. Validate Source
        if (booking.getSourceStation() == null) {
            throw new InvalidRequestException(
                    "Source station is required"
            );
        }

        // 5. Validate Destination
        if (booking.getDestinationStation() == null) {
            throw new InvalidRequestException(
                    "Destination station is required"
            );
        }

        // 6. Source and destination cannot be same
        if (booking.getSourceStation()
                .equals(booking.getDestinationStation())) {

            throw new InvalidRequestException(
                    "Source and destination cannot be same"
            );
        }

        // 7. Validate fare
        if (booking.getTotalFare() == null) {
            throw new InvalidRequestException(
                    "Total fare is required"
            );
        }

        if (booking.getTotalFare().signum() <= 0) {
            throw new InvalidRequestException(
                    "Total fare must be greater than 0"
            );
        }

        // =====================================================
        // GENERATE UNIQUE PNR
        // =====================================================

        String pnr;

        do {
            pnr = String.valueOf(
                    1000000000L +
                            (long) (Math.random() * 900000000L)
            );

        } while (bookingRepository.existsBypnr(pnr));

        // =====================================================
        // SET BOOKING INFORMATION
        // =====================================================

        booking.setPnr(pnr);

        booking.setBookingDateTime(
                LocalDateTime.now()
        );

        // New booking starts as PENDING
        booking.setBookingStatus(
                BookingStatus.PENDING
        );

        // =====================================================
        // SAVE BOOKING
        // =====================================================

        return bookingRepository.save(booking);
    }


    // =========================================================
    // GET BOOKING BY ID
    // =========================================================

    @Override
    public Booking getBookingById(Long id) {

        Optional<Booking> bookingOptional =
                bookingRepository.findById(id);

        if (bookingOptional.isPresent()) {
            return bookingOptional.get();
        }

        throw new ResourceNotFoundException(
                "Booking with id " + id + " not found"
        );
    }


    // =========================================================
    // GET ALL BOOKINGS
    // =========================================================

    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }


    // =========================================================
    // UPDATE BOOKING
    // =========================================================

    @Override
    public Booking updateBooking(
            Long id,
            Booking booking) {

        Optional<Booking> optional =
                bookingRepository.findById(id);

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException(
                    "Booking not found"
            );
        }

        Booking existingBooking =
                optional.get();

        // =====================================================
        // PNR CANNOT BE CHANGED
        // =====================================================

        if (booking.getPnr() == null ||
                !existingBooking.getPnr()
                        .equals(booking.getPnr())) {

            throw new InvalidRequestException(
                    "PNR cannot be updated"
            );
        }

        // =====================================================
        // USER CANNOT BE CHANGED
        // =====================================================

        if (booking.getUser() == null ||
                !existingBooking.getUser()
                        .equals(booking.getUser())) {

            throw new InvalidRequestException(
                    "User cannot be changed"
            );
        }

        // =====================================================
        // TRAIN JOURNEY CANNOT BE CHANGED
        // =====================================================

        if (booking.getTrainJourney() == null ||
                !existingBooking.getTrainJourney()
                        .equals(booking.getTrainJourney())) {

            throw new InvalidRequestException(
                    "Train journey cannot be changed"
            );
        }

        // =====================================================
        // UPDATE ALLOWED FIELDS
        // =====================================================

        if (booking.getSourceStation() != null) {
            existingBooking.setSourceStation(
                    booking.getSourceStation()
            );
        }

        if (booking.getDestinationStation() != null) {
            existingBooking.setDestinationStation(
                    booking.getDestinationStation()
            );
        }

        if (booking.getBookingStatus() != null) {
            existingBooking.setBookingStatus(
                    booking.getBookingStatus()
            );
        }

        if (booking.getTotalFare() != null) {
            existingBooking.setTotalFare(
                    booking.getTotalFare()
            );
        }

        return bookingRepository.save(
                existingBooking
        );
    }


    // =========================================================
    // CANCEL BOOKING
    // =========================================================

    @Override
    @Transactional
    public Booking cancelBooking(Long id) {

        // 1. Find booking
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        // 2. Check booking status
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new InvalidRequestException("Booking already cancelled");
        }

        // 3. Get passengers
        List<Passenger> passengers =
                passengerService.getPassengerByBooking(booking);

        // 4. Release seats
        for (Passenger passenger : passengers) {

            SeatInventory inventory =
                    passenger.getSeatInventory();

            if (inventory != null) {
                inventory.setSeatStatus(SeatStatus.AVAILABLE);
                inventory.setActive(true);

                seatInventoryRepository.save(inventory);
            }

            // Optional: deactivate passenger
            passenger.setActive(false);
            passengerRepository.save(passenger);
        }

        // 5. Change booking status
        booking.setBookingStatus(
                BookingStatus.CANCELLED
        );

        // 6. Save booking
        return bookingRepository.save(booking);
    }


    // =========================================================
    // GET BOOKING BY PNR
    // =========================================================

    @Override
    public Booking getBookingByPnr(String pnr) {

        Optional<Booking> optional =
                bookingRepository.findByPnr(pnr);

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException(
                    "Booking not found"
            );
        }

        return optional.get();
    }


    // =========================================================
    // GET BOOKINGS BY USER
    // =========================================================

    @Override
    public List<Booking> getBookingsByUser(
            User user) {

        List<Booking> bookings =
                bookingRepository.findByUser(user);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Booking not found"
            );
        }

        return bookings;
    }


    // =========================================================
    // GET BOOKINGS BY TRAIN JOURNEY
    // =========================================================

    @Override
    public List<Booking> getBookingsByTrainJourney(
            TrainJourney trainJourney) {

        List<Booking> bookings =
                bookingRepository
                        .findByTrainJourney(trainJourney);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Booking not found"
            );
        }

        return bookings;
    }
}