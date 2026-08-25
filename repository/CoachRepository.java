package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    List<Coach> findByTrain(Train train);
    Boolean existsByTrainAndCoachNumber(Train train,String coachNumber);

}
