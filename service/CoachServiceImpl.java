package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.DuplicateResourceException;
@Service
public class CoachServiceImpl implements CoachService {
    private final CoachRepository coachRepository;
    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public Coach createCoach(Coach coach) {
        if(coachRepository.existsByTrainAndCoachNumber(coach.getTrain(),coach.getCoachNumber())){
            throw new DuplicateResourceException("Already exists coach");
        }
       return  coachRepository.save(coach);

    }

    @Override
    public Coach getCoachById(Long id) {
        Optional<Coach> coach = coachRepository.findById(id);
        if(coach.isPresent()){
            return coach.get();
        }
        throw new ResourceNotFoundException("coach not found");
    }

    @Override
    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }
    @Override
    public Coach updateCoach(Long id, Coach coach) {

        Coach existingCoach =
                coachRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Coach not found"
                                ));

        if (coach.getTrain() == null ||
                coach.getTrain().getId() == null) {

            throw new IllegalArgumentException(
                    "Train is required"
            );
        }

        if (coach.getCoachNumber() == null ||
                coach.getCoachNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Coach number is required"
            );
        }

        boolean duplicate =
                coachRepository
                        .existsByTrainAndCoachNumberAndIdNot(
                                coach.getTrain(),
                                coach.getCoachNumber(),
                                id
                        );

        if (duplicate) {
            throw new DuplicateResourceException(
                    "Coach already exists for this train"
            );
        }

        existingCoach.setTrain(coach.getTrain());
        existingCoach.setCoachNumber(
                coach.getCoachNumber()
        );
        existingCoach.setCoachType(
                coach.getCoachType()
        );

        return coachRepository.save(existingCoach);
    }
    @Override
    public Coach deleteCoach(Long id) {
        Optional<Coach> oldCoach = coachRepository.findById(id);
        if(oldCoach.isPresent()){
            coachRepository.deleteById(id);
            return oldCoach.get();
        }
        throw new ResourceNotFoundException("coach not found");
    }

    @Override
    public List<Coach> getCoachesByTrain(Train train) {
        return coachRepository.findByTrain(train);
    }
}
