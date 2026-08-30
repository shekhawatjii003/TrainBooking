package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.DuplicateResourceException;

@Service
public class SeatServiceImpl implements SeatService {
private final SeatRepository seatRepository;
public SeatServiceImpl(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
}
    @Override
    public Seat createSeat(Seat seat) {
        if(seatRepository.existsByCoachAndSeatNumber(seat.getCoach(),seat.getSeatNumber())){
            throw new DuplicateResourceException("Already exists");
        }
        return seatRepository.save(seat);
    }

    @Override
    public Seat getSeatById(Long id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if(seat.isPresent()){
            return seat.get();
        }
        throw new ResourceNotFoundException("Not found");
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
    @Override
    public Seat updateSeat(Long id, Seat seat) {

        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found"
                        ));
        if (seat.getCoach() == null ||
                seat.getCoach().getId() == null) {

            throw new IllegalArgumentException(
                    "Coach is required"
            );
        }
        if (seat.getSeatNumber() == null) {

            throw new IllegalArgumentException(
                    "Seat number is required"
            );
        }
        boolean duplicate =
                seatRepository
                        .existsByCoachAndSeatNumberAndIdNot(
                                seat.getCoach(),
                                seat.getSeatNumber(),
                                id
                        );
        if (duplicate) {

            throw new DuplicateResourceException(
                    "Seat already exists for this coach"
            );
        }
        existingSeat.setCoach(seat.getCoach());
        existingSeat.setSeatNumber(
                seat.getSeatNumber()
        );
        existingSeat.setSeatType(
                seat.getSeatType()
        );
        existingSeat.setActive(
                seat.getActive()
        );
        return seatRepository.save(existingSeat);
    }

    @Override
    public Seat deleteSeat(Long id) {
        Optional<Seat> oldSeat = seatRepository.findById(id);
        if(oldSeat.isPresent()){
            oldSeat.get().setActive(false);
            return seatRepository.save(oldSeat.get());
        }
        throw new ResourceNotFoundException("Not found");
    }

    @Override
    public List<Seat> getSeatsByCoach(Coach coach) {
        return seatRepository.findByCoach(coach);
    }
}
