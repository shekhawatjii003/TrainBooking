package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.BookingStatus;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class BookingServiceImpl implements BookingService{
    private BookingRepository bookingRepository;
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking createBooking(Booking booking) {

        // 1. Validate User
        if (booking.getUser() == null) {
            throw new RuntimeException("User is required");
        }

        // 2. Validate Train Journey
        if (booking.getTrainJourney() == null) {
            throw new RuntimeException("Train Journey is required");
        }

        if (!booking.getTrainJourney().getActive()) {
            throw new RuntimeException("Train Journey is not active");
        }

        // 3. Validate Source and Destination
        if (booking.getSourceStation() == null) {
            throw new RuntimeException("Source station is required");
        }

        if (booking.getDestinationStation() == null) {
            throw new RuntimeException("Destination station is required");
        }

        if (booking.getSourceStation().equals(booking.getDestinationStation())) {
            throw new RuntimeException(
                    "Source and destination cannot be same"
            );
        }

        // 4. Generate unique PNR
        String pnr;

        do {
            pnr = String.valueOf(
                    1000000000L + (long) (Math.random() * 900000000L)
            );
        } while (bookingRepository.existsBypnr(pnr));

        // 5. Set booking information
        booking.setPnr(pnr);
        booking.setBookingDateTime(LocalDateTime.now());

        // 6. Initial booking status
        booking.setBookingStatus(BookingStatus.PENDING);

        // 7. Save booking
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            return bookingOptional.get();
        }
        throw new RuntimeException("Booking with id " + id + " not found");
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {

        Optional<Booking> optional = bookingRepository.findById(id);

        if (!optional.isPresent()) {
            throw new RuntimeException("Booking not found");
        }

        Booking existingBooking = optional.get();

        // PNR cannot be changed
        if (!existingBooking.getPnr().equals(booking.getPnr())) {
            throw new RuntimeException("PNR cannot be updated");
        }

        // User cannot be changed
        if (!existingBooking.getUser().equals(booking.getUser())) {
            throw new RuntimeException("User cannot be changed");
        }

        // Train journey cannot be changed
        if (!existingBooking.getTrainJourney().equals(booking.getTrainJourney())) {
            throw new RuntimeException("Train journey cannot be changed");
        }

        // Update allowed fields
        existingBooking.setSourceStation(booking.getSourceStation());
        existingBooking.setDestinationStation(booking.getDestinationStation());
        existingBooking.setBookingStatus(booking.getBookingStatus());
        existingBooking.setTotalFare(booking.getTotalFare());

        return bookingRepository.save(existingBooking);
    }

    @Override
    public Booking cancelBooking(Long id) {
        Optional<Booking> optional = bookingRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Booking not found");
        }

        Booking booking = optional.get();
        if(booking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
            throw new RuntimeException("Booking already cancelled");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingByPnr(String pnr) {
        Optional<Booking>  optional = bookingRepository.findByPnr(pnr);
        if (!optional.isPresent()) {
            throw new RuntimeException("Booking not found");
        }
        return optional.get();
    }

    @Override
    public List<Booking> getBookingsByUser(User user) {
        List<Booking> bookings = bookingRepository.findByUser(user);
        if(bookings.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByTrainJourney(TrainJourney trainJourney) {
        List<Booking> bookings = bookingRepository.findByTrainJourney(trainJourney);
        if(bookings.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        return bookings;
    }
}
