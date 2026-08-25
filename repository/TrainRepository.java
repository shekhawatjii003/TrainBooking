package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNumber(Long trainNumber);
    Optional<Train> findByTrainName(String trainName);
    List<Train> findByActiveTrue();

}
