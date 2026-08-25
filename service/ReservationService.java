package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.entity.TrainJourney;

public interface ReservationService {

    void reserveSeat(
            TrainJourney trainJourney,
            Seat seat,
            Booking booking
    );

    void confirmReservation(
            TrainJourney trainJourney,
            Seat seat,
            Booking booking
    );

    void releaseSeat(
            TrainJourney trainJourney,
            Seat seat,
            Booking booking
    );
}