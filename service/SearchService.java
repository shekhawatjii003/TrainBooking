package com.example.trainbooking.service;

import com.example.trainbooking.entity.TrainJourney;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {

    List<TrainJourney> searchTrains(
            String fromStationCode,
            String toStationCode,
            LocalDate journeyDate
    );
}