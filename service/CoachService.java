package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Train;

import java.util.List;

public interface CoachService {
    Coach createCoach(Coach coach);
    Coach getCoachById(Long id);
    List<Coach> getCoaches();
    Coach updateCoach(Long id,Coach coach);
    Coach deleteCoach(Long id);
    List<Coach> getCoachesByTrain(Train train);

}
