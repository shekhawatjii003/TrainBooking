package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainJourney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TrainJourneyRepository extends JpaRepository<TrainJourney, Long> {
    Optional<TrainJourney> findByTrainAndJourneyDate(Train train, LocalDate journeyDate);
    Boolean existsByTrainAndJourneyDate(Train train, LocalDate journeyDate);
}
