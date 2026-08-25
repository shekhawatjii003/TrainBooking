package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger createPassenger(Passenger passenger);
    Passenger getPassenger(Long id);
    List<Passenger> getPassengers();
    Passenger updatePassenger(Long id,Passenger passenger);
    Passenger deletePassenger(Long id);
    List<Passenger> getPassengerByBooking(Booking booking);

}
