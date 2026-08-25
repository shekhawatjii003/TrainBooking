package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;

import java.util.List;

public interface ReservationBookingService {

    Booking book(
            Booking booking,
            List<Passenger> passengers
    );
}
