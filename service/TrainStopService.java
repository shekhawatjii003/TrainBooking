package com.example.trainbooking.service;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainStop;

import java.util.List;

public interface TrainStopService {
    TrainStop createTrainStop(TrainStop trainStop);
    TrainStop getTrainStopById(Long id);
    List<TrainStop> getAllTrainStops();
    TrainStop updateTrainStop(Long id,TrainStop trainStop);
    TrainStop deleteTrainStop(Long id);
    List<TrainStop> getTrainStopsByTrain(Train train);
    TrainStop getTrainStopByTrainAndStation(Train train, Station station);
    List<TrainStop> getTrainRoute(Train train);
}
