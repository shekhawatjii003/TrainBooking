package com.example.trainbooking.service;

import com.example.trainbooking.entity.*;
import com.example.trainbooking.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.SeatNotAvailableException;

@Service
public class ReservationServiceImpl implements ReservationService{
    private  final SeatInventoryService seatInventoryService;
    private final PassengerRepository passengerRepository;
    public  ReservationServiceImpl(SeatInventoryService seatInventoryService,PassengerRepository passengerRepository){
        this.seatInventoryService = seatInventoryService;
        this.passengerRepository=passengerRepository;
    }
    @Override
    public void reserveSeat(TrainJourney trainJourney, Seat seat, Booking booking) {
        SeatInventory inventory =
                seatInventoryService.getInventoryByJourneyAndSeat(trainJourney, seat);
        if (inventory == null) {
            throw new ResourceNotFoundException("Seat inventory not found");
        }
        if (!inventory.getActive()) {
            throw new SeatNotAvailableException("Seat inventory is not active");
        }
        if (inventory.getSeatStatus() != SeatStatus.AVAILABLE) {
            throw new SeatNotAvailableException("Seat is not available");
        }
        inventory.setSeatStatus(SeatStatus.LOCKED);
        seatInventoryService.updateSeatInventory(
                inventory.getId(),
                inventory
        );
    }
    @Override
    public void confirmReservation(TrainJourney trainJourney, Seat seat, Booking booking) {
    SeatInventory inventory=seatInventoryService.getInventoryByJourneyAndSeat(trainJourney, seat);
    if (inventory == null) {
            throw new ResourceNotFoundException("Seat inventory not found");
    }
    if (!inventory.getActive()) {
        throw new SeatNotAvailableException("Seat inventory is not active");
    }
    if (inventory.getSeatStatus() == SeatStatus.LOCKED ) {
        Optional<Passenger> passenger=passengerRepository.findByBookingAndSeatInventory(booking,inventory);
        if (passenger.isPresent()) {
            inventory.setSeatStatus(SeatStatus.BOOKED);
            seatInventoryService.updateSeatInventory(inventory.getId(), inventory);
        }
        else{
            throw new SeatNotAvailableException("Passenger is different");
        }
    }
    else{
        throw new SeatNotAvailableException("Seat is not locked");
    }
    }

    @Override
    public void releaseSeat(TrainJourney trainJourney, Seat seat, Booking booking) {
SeatInventory inventory=seatInventoryService.getInventoryByJourneyAndSeat(trainJourney, seat);
if (inventory == null) {
    throw new ResourceNotFoundException("Seat inventory not found");
}
if (!inventory.getActive()) {
    throw new SeatNotAvailableException("Seat inventory is not active");
}
if (inventory.getSeatStatus() == SeatStatus.LOCKED) {
    Optional<Passenger>passenger=passengerRepository.findByBookingAndSeatInventory(booking,inventory);
    if (passenger.isPresent()) {
        inventory.setSeatStatus(SeatStatus.AVAILABLE);
        seatInventoryService.updateSeatInventory(inventory.getId(), inventory);
    }
    else{
        throw new SeatNotAvailableException("Passenger is different");
    }
}
else{
        throw new SeatNotAvailableException("Seat is not locked");
}
    }
}
