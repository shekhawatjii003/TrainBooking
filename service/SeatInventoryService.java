package com.example.trainbooking.service;

import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.entity.SeatInventory;
import com.example.trainbooking.entity.SeatStatus;
import com.example.trainbooking.entity.TrainJourney;

import java.util.List;

public interface SeatInventoryService {

    SeatInventory createSeatInventory(SeatInventory seatInventory);

    SeatInventory getSeatInventoryById(Long id);

    List<SeatInventory> getAllSeatInventories();

    SeatInventory updateSeatInventory(Long id, SeatInventory seatInventory);

    SeatInventory deleteSeatInventory(Long id);

    List<SeatInventory> getInventoryByTrainJourney(TrainJourney trainJourney);

    List<SeatInventory> getInventoryByJourneyAndStatus(
            TrainJourney trainJourney,
            SeatStatus status
    );

    SeatInventory getInventoryByJourneyAndSeat(
            TrainJourney trainJourney,
            Seat seat
    );
}