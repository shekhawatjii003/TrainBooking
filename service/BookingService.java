package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.entity.User;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking getBookingById(Long id);

    List<Booking> getAllBookings();

    Booking updateBooking(Long id, Booking booking);

    Booking cancelBooking(Long id);

    Booking getBookingByPnr(String pnr);

    List<Booking> getBookingsByUser(User user);

    List<Booking> getBookingsByTrainJourney(TrainJourney trainJourney);
}