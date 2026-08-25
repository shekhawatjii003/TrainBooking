package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Gender;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.entity.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByBooking(Booking booking);
    boolean existsByBookingAndNameAndAgeAndGender(
            Booking booking,
            String name,
            Integer age,
            Gender gender
    );
    Optional<Passenger> findByBookingAndSeatInventory(
            Booking booking,
            SeatInventory seatInventory
    );
}
