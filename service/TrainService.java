package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;

import java.util.List;

public interface TrainService {
    Train createTrain(Train train);
    Train getTrainById(Long id);
    List<Train> getAllTrains();
    Train updateTrain(Long id,Train train);
    Train deleteTrain(Long id);
    Train getTrainByNumber(Long number);
    List<Train> getAllActiveTrains();
    Train searchTrain(String name);
}
