package com.example.trainbooking.service;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TrainServiceImpl implements TrainService {
    private final TrainRepository trainRepository;
    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public Train createTrain(Train train) {
        if(trainRepository.findByTrainNumber(train.getTrainNumber())!=null){
            throw new RuntimeException("Already exist train number");
        }
        return trainRepository.save(train);
    }

    @Override
    public Train getTrainById(Long id) {
        Optional<Train> train = trainRepository.findById(id);
        if(!train.isPresent()){
            throw new RuntimeException("Train not found");
        }
        return train.get();
    }

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train updateTrain(Long id, Train train) {
        Optional<Train>optional=trainRepository.findById(id);

        if(!optional.isPresent()){
            throw new RuntimeException("Train not found");
        }
        if(!optional.get().getTrainNumber().equals(train.getTrainNumber())){
            throw new RuntimeException("Train number can not be updated");
        }
        Train existing=optional.get();
        existing.setTrainName(train.getTrainName());
        existing.setActive(train.isActive());
        existing.setTrainType(train.getTrainType());
        return trainRepository.save(existing);

    }

    @Override
    public Train deleteTrain(Long id) {
        Optional<Train> train = trainRepository.findById(id);
        if(!train.isPresent()){
            throw new RuntimeException("Train not found");
        }
        train.get().setActive(false);
        return trainRepository.save(train.get());
    }

    @Override
    public Train getTrainByNumber(Long number) {
        Optional<Train> train = trainRepository.findByTrainNumber(number);
        if(!train.isPresent()){
            throw new RuntimeException("Train not found");
        }
        return train.get();
    }

    @Override
    public List<Train> getAllActiveTrains() {
       return  trainRepository.findByActiveTrue();
    }

    @Override
    public Train searchTrain(String name) {
        Optional<Train> train=trainRepository.findByTrainName(name);
        if(!train.isPresent()){
            throw new RuntimeException("Train not found");
        }
        return train.get();
    }
}
