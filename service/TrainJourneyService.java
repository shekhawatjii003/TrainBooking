package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainJourney;

import java.time.LocalDate;
import java.util.List;

public interface TrainJourneyService {

    TrainJourney createTrainJourney(TrainJourney trainJourney);

    TrainJourney getTrainJourneyById(Long id);

    List<TrainJourney> getAllTrainJourneys();

    TrainJourney updateTrainJourney(Long id, TrainJourney trainJourney);

    TrainJourney deleteTrainJourney(Long id);

    TrainJourney getJourneyByTrainAndDate(Train train, LocalDate journeyDate);
}