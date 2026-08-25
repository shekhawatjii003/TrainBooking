package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByBooking(Booking booking);

}
