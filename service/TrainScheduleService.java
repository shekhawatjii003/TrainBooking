package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainSchedule;

import java.time.DayOfWeek;
import java.util.List;

public interface TrainScheduleService {

    TrainSchedule createTrainSchedule(TrainSchedule trainSchedule);
    TrainSchedule getTrainScheduleById(Long id);
    List<TrainSchedule> getAllSchedules();
    TrainSchedule updateSchedule(Long id, TrainSchedule trainSchedule);
    TrainSchedule deleteTrainScheduleById(Long id);
    TrainSchedule getScheduleByTrainAndDay(Train train, DayOfWeek dayOfWeek);
    List<TrainSchedule> getAllActiveSchedules();
}