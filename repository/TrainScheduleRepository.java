package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    Optional<TrainSchedule> findByTrainAndDayOfWeekAndActiveTrue(Train train, DayOfWeek dayOfWeek);
    List<TrainSchedule>findAllByActiveTrue();
    Boolean existsByTrainAndDayOfWeek(Train train, DayOfWeek dayOfWeek);

}
