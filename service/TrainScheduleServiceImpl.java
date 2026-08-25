package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainSchedule;
import com.example.trainbooking.repository.TrainScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class TrainScheduleServiceImpl implements TrainScheduleService{
    private final TrainScheduleRepository trainScheduleRepository;
    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
    }
    @Override
    public TrainSchedule createTrainSchedule(TrainSchedule trainSchedule) {
        if(trainScheduleRepository.existsByTrainAndDayOfWeek(trainSchedule.getTrain(), trainSchedule.getDayOfWeek())){
            throw new RuntimeException("Already exists");
        }
        return trainScheduleRepository.save(trainSchedule);
    }

    @Override
    public TrainSchedule getTrainScheduleById(Long id) {
        Optional<TrainSchedule> trainSchedule = trainScheduleRepository.findById(id);
        if(trainSchedule.isPresent()){
            return trainSchedule.get();
        }
        throw new RuntimeException("Train Schedule Not Found");
    }

    @Override
    public List<TrainSchedule> getAllSchedules() {
        return trainScheduleRepository.findAll();
    }

    @Override
    public TrainSchedule updateSchedule(Long id, TrainSchedule trainSchedule) {

        Optional<TrainSchedule> optional = trainScheduleRepository.findById(id);

        if (!optional.isPresent()) {
            throw new RuntimeException("Train Schedule Not Found");
        }

        TrainSchedule existingSchedule = optional.get();

        // Check if Train + DayOfWeek is being changed
        boolean trainChanged =
                !existingSchedule.getTrain().equals(trainSchedule.getTrain());

        boolean dayChanged =
                !existingSchedule.getDayOfWeek().equals(trainSchedule.getDayOfWeek());

        if (trainChanged || dayChanged) {

            if (trainScheduleRepository.existsByTrainAndDayOfWeek(
                    trainSchedule.getTrain(),
                    trainSchedule.getDayOfWeek())) {

                throw new RuntimeException("Schedule already exists for this train and day");
            }
        }

        existingSchedule.setTrain(trainSchedule.getTrain());
        existingSchedule.setDayOfWeek(trainSchedule.getDayOfWeek());
        existingSchedule.setActive(trainSchedule.isActive());

        return trainScheduleRepository.save(existingSchedule);
    }
    @Override
    public TrainSchedule deleteTrainScheduleById(Long id) {
        Optional<TrainSchedule> trainSchedule = trainScheduleRepository.findById(id);
        if(trainSchedule.isPresent()){
            TrainSchedule train=trainSchedule.get();
            train.setActive(false);
            return trainScheduleRepository.save(train);
        }
        throw new RuntimeException("Train Schedule Not Found");
    }

    @Override
    public TrainSchedule getScheduleByTrainAndDay(Train train, DayOfWeek dayOfWeek) {
        Optional<TrainSchedule>schedule=trainScheduleRepository.findByTrainAndDayOfWeekAndActiveTrue(train,dayOfWeek);
        if(schedule.isPresent()){
            return schedule.get();
        }
        throw new RuntimeException("Train Schedule Not Found");
    }

    @Override
    public List<TrainSchedule> getAllActiveSchedules() {
        List<TrainSchedule>schedules=trainScheduleRepository.findAllByActiveTrue();
         return schedules;
    }
}
