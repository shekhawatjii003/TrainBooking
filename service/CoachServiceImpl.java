package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CoachServiceImpl implements CoachService {
    private final CoachRepository coachRepository;
    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public Coach createCoach(Coach coach) {
        if(coachRepository.existsByTrainAndCoachNumber(coach.getTrain(),coach.getCoachNumber())){
            throw new RuntimeException("Already exists coach");
        }
       return  coachRepository.save(coach);

    }

    @Override
    public Coach getCoachById(Long id) {
        Optional<Coach> coach = coachRepository.findById(id);
        if(coach.isPresent()){
            return coach.get();
        }
        throw new RuntimeException("coach not found");
    }

    @Override
    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }

    @Override
    public Coach updateCoach(Long id, Coach coach) {
        Optional<Coach> optional = coachRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Coach not found");
        }
        Coach existingCoach = optional.get();
        boolean trainChanged =
                !existingCoach.getTrain().equals(coach.getTrain());
        boolean coachNumberChanged =
                !existingCoach.getCoachNumber().equals(coach.getCoachNumber());
        if (trainChanged || coachNumberChanged) {
            if (coachRepository.existsByTrainAndCoachNumber(
                    coach.getTrain(),
                    coach.getCoachNumber())) {
                throw new RuntimeException("Coach already exists for this train");
            }
        }
        existingCoach.setTrain(coach.getTrain());
        existingCoach.setCoachNumber(coach.getCoachNumber());
        existingCoach.setCoachType(coach.getCoachType());

        return coachRepository.save(existingCoach);
    }
    @Override
    public Coach deleteCoach(Long id) {
        Optional<Coach> oldCoach = coachRepository.findById(id);
        if(oldCoach.isPresent()){
            coachRepository.deleteById(id);
            return oldCoach.get();
        }
        throw new RuntimeException("coach not found");
    }

    @Override
    public List<Coach> getCoachesByTrain(Train train) {
        return coachRepository.findByTrain(train);
    }
}
