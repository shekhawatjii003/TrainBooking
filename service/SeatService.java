package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Seat;

import java.util.List;

public interface SeatService {

    Seat createSeat(Seat seat);

    Seat getSeatById(Long id);

    List<Seat> getAllSeats();

    Seat updateSeat(Long id, Seat seat);

    Seat deleteSeat(Long id);

    List<Seat> getSeatsByCoach(Coach coach);
}