package com.example.trainbooking.service;

import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.entity.SeatInventory;
import com.example.trainbooking.entity.SeatStatus;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.repository.SeatInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.DuplicateResourceException;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService{
    private final SeatInventoryRepository seatInventoryRepository;
    public SeatInventoryServiceImpl(SeatInventoryRepository seatInventoryRepository) {
        this.seatInventoryRepository = seatInventoryRepository;
    }
    @Override
    public SeatInventory createSeatInventory(SeatInventory seatInventory) {
        if(seatInventoryRepository.existsByTrainJourneyAndSeat(seatInventory.getTrainJourney(), seatInventory.getSeat())){
            throw new DuplicateResourceException("Already exists");
        }
        return seatInventoryRepository.save(seatInventory);
    }

    @Override
    public SeatInventory getSeatInventoryById(Long id) {
        Optional<SeatInventory> seatInventory = seatInventoryRepository.findById(id);
        if(seatInventory.isPresent()){
            return seatInventory.get();
        }
        throw new ResourceNotFoundException("Not found");
    }

    @Override
    public List<SeatInventory> getAllSeatInventories() {
        return seatInventoryRepository.findAll();
    }

    @Override
    public SeatInventory updateSeatInventory(Long id, SeatInventory seatInventory) {

        Optional<SeatInventory> optional = seatInventoryRepository.findById(id);

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Seat Inventory not found");
        }
        SeatInventory existingInventory = optional.get();
        existingInventory.setSeatStatus(seatInventory.getSeatStatus());
        return seatInventoryRepository.save(existingInventory);
    }

    @Override
    public SeatInventory deleteSeatInventory(Long id) {
        Optional<SeatInventory> seatInventory = seatInventoryRepository.findById(id);
        if(seatInventory.isPresent()){
            seatInventory.get().setActive(false);
            return seatInventoryRepository.save(seatInventory.get());
        }
        throw new ResourceNotFoundException("Not found");
    }

    @Override
    public List<SeatInventory> getInventoryByTrainJourney(TrainJourney trainJourney) {
        return seatInventoryRepository.findByTrainJourney(trainJourney);
    }

    @Override
    public List<SeatInventory> getInventoryByJourneyAndStatus(TrainJourney trainJourney, SeatStatus status) {
        return seatInventoryRepository.findByTrainJourneyAndSeatStatus(trainJourney, status);
    }

    @Override
    public SeatInventory getInventoryByJourneyAndSeat(TrainJourney trainJourney, Seat seat) {
        Optional<SeatInventory> seatInventory=seatInventoryRepository.findByTrainJourneyAndSeat(trainJourney,seat);
        if(seatInventory.isPresent()){
            return seatInventory.get();
        }
        throw new ResourceNotFoundException("Not found");
    }
}
