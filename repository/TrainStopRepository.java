package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainStopRepository extends JpaRepository<TrainStop, Long> {
    List<TrainStop> findByTrain(Train train);
    Optional<TrainStop> findByTrainAndStation(Train train, Station station);
    List<TrainStop> findByTrainOrderByStopSequenceAsc(Train train);
    List<TrainStop> findByStation(Station station);


}
