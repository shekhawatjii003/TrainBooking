package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.entity.SeatInventory;
import com.example.trainbooking.entity.SeatStatus;
import com.example.trainbooking.entity.TrainJourney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {
    List<SeatInventory> findByTrainJourney(TrainJourney trainJourney);
    List<SeatInventory> findByTrainJourneyAndStatus(TrainJourney trainJourney, SeatStatus status);
    Optional<SeatInventory> findByTrainJourneyAndSeat(TrainJourney trainJourney, Seat seat);
    Boolean existsByTrainJourneyAndSeat(TrainJourney trainJourney, Seat seat);
}
