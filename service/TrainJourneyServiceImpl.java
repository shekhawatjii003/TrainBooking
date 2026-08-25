package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.repository.TrainJourneyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainJourneyServiceImpl implements TrainJourneyService {
    private final TrainJourneyRepository trainJourneyRepository;
    public TrainJourneyServiceImpl(TrainJourneyRepository trainJourneyRepository) {
        this.trainJourneyRepository = trainJourneyRepository;
    }
    @Override
    public TrainJourney createTrainJourney(TrainJourney trainJourney) {
        if(trainJourneyRepository.existsByTrainAndJourneyDate(trainJourney.getTrain(),trainJourney.getJourneyDate())){
            throw new RuntimeException("TrainJourney already exists");
        }
        return trainJourneyRepository.save(trainJourney);
    }

    @Override
    public TrainJourney getTrainJourneyById(Long id) {
        Optional<TrainJourney> trainJourney = trainJourneyRepository.findById(id);
        if(trainJourney.isPresent()){
            return trainJourney.get();
        }
        throw new RuntimeException("TrainJourney not found");
    }

    @Override
    public List<TrainJourney> getAllTrainJourneys() {
        return trainJourneyRepository.findAll();
    }

    @Override
    public TrainJourney updateTrainJourney(Long id, TrainJourney trainJourney) {
        Optional<TrainJourney> optional = trainJourneyRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Train Journey not found");
        }
        TrainJourney existingJourney = optional.get();
        boolean trainChanged =
                !existingJourney.getTrain().equals(trainJourney.getTrain());
        boolean dateChanged =
                !existingJourney.getJourneyDate().equals(trainJourney.getJourneyDate());
        if (trainChanged || dateChanged) {
            Optional<TrainJourney> duplicate =
                    trainJourneyRepository.findByTrainAndJourneyDate(
                            trainJourney.getTrain(),
                            trainJourney.getJourneyDate()
                    );

            if (duplicate.isPresent()) {
                throw new RuntimeException(
                        "Journey already exists for this train on this date"
                );
            }
        }

        existingJourney.setTrain(trainJourney.getTrain());
        existingJourney.setJourneyDate(trainJourney.getJourneyDate());

        return trainJourneyRepository.save(existingJourney);
    }


    @Override
    public TrainJourney deleteTrainJourney(Long id) {
        Optional<TrainJourney> optional = trainJourneyRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Train Journey not found");
        }
        TrainJourney existingJourney = optional.get();
        existingJourney.setActive(false);
        return  trainJourneyRepository.save(existingJourney);
    }

    @Override
    public TrainJourney getJourneyByTrainAndDate(Train train, LocalDate journeyDate) {
        Optional<TrainJourney>trainJourney = trainJourneyRepository.findByTrainAndJourneyDate(train, journeyDate);
        if(trainJourney.isPresent()){
            return trainJourney.get();
        }
        throw new RuntimeException("Train Journey not found");
    }
}
