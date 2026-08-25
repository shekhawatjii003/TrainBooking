package com.example.trainbooking.service;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainStop;
import com.example.trainbooking.repository.TrainStopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TrainStopServiceImpl implements TrainStopService{
    private final TrainStopRepository trainStopRepository;
    public TrainStopServiceImpl(TrainStopRepository trainStopRepository) {
        this.trainStopRepository = trainStopRepository;
    }
    @Override
    public TrainStop createTrainStop(TrainStop trainStop) {
        if(trainStopRepository.findByTrainAndStation(trainStop.getTrain(), trainStop.getStation()).isPresent()){
            throw new RuntimeException("Already exists in TrainStop");
        }
        return trainStopRepository.save(trainStop);
    }

    @Override
    public TrainStop getTrainStopById(Long id) {
        Optional<TrainStop> trainStop = trainStopRepository.findById(id);
        if(trainStop.isPresent()){
            return trainStop.get();
        }
        throw new RuntimeException("TrainStop not found");
    }

    @Override
    public List<TrainStop> getAllTrainStops() {
        return trainStopRepository.findAll();
    }

    @Override
    public TrainStop updateTrainStop(Long id, TrainStop trainStop) {
        Optional<TrainStop> trainStopOptional = trainStopRepository.findById(id);
        if(trainStopOptional.isPresent()){
            TrainStop trainStopEntity = trainStopOptional.get();
            trainStopEntity.setStation(trainStop.getStation());
            trainStopEntity.setArrivalTime(trainStop.getArrivalTime());
            trainStopEntity.setDepartureTime(trainStop.getDepartureTime());
            trainStopEntity.setStopSequence(trainStop.getStopSequence());
            trainStopEntity.setDistanceFromSource(trainStop.getDistanceFromSource());
            return trainStopRepository.save(trainStopEntity);

        }
        throw new RuntimeException("TrainStop not found");
    }

    @Override
    public TrainStop deleteTrainStop(Long id) {
        Optional<TrainStop> trainStop = trainStopRepository.findById(id);
        if(trainStop.isPresent()){
            TrainStop trainStopEntity = trainStop.get();
            trainStopEntity.setActive(false);
            return trainStopRepository.save(trainStopEntity);
        }
        throw new RuntimeException("TrainStop not found");
    }

    @Override
    public List<TrainStop> getTrainStopsByTrain(Train train) {
        return trainStopRepository.findByTrainOrderByStopSequenceAsc(train);
    }

    @Override
    public TrainStop getTrainStopByTrainAndStation(Train train, Station station) {
       Optional<TrainStop> trainStop = trainStopRepository.findByTrainAndStation(train, station);
       if(trainStop.isPresent()){
           return trainStop.get();
       }
       throw new RuntimeException("TrainStop not found");
    }

    @Override
    public List<TrainStop> getTrainRoute(Train train) {
        return trainStopRepository.findByTrainOrderByStopSequenceAsc(train);
    }
}
