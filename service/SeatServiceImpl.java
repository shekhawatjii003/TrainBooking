package com.example.trainbooking.service;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {
private final SeatRepository seatRepository;
public SeatServiceImpl(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
}
    @Override
    public Seat createSeat(Seat seat) {
        if(seatRepository.existsByCoachAndSeatNumber(seat.getCoach(),seat.getSeatNumber())){
            throw new RuntimeException("Already exists");
        }
        return seatRepository.save(seat);
    }

    @Override
    public Seat getSeatById(Long id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if(seat.isPresent()){
            return seat.get();
        }
        throw new RuntimeException("Not found");
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    public Seat updateSeat(Long id, Seat seat) {

        Optional<Seat> optional = seatRepository.findById(id);

        if (!optional.isPresent()) {
            throw new RuntimeException("Seat not found");
        }
        Seat existingSeat = optional.get();
        boolean coachChanged =
                !existingSeat.getCoach().equals(seat.getCoach());
        boolean seatNumberChanged =
                !existingSeat.getSeatNumber().equals(seat.getSeatNumber());
        if (coachChanged || seatNumberChanged) {
            if (seatRepository.existsByCoachAndSeatNumber(
                    seat.getCoach(),
                    seat.getSeatNumber())) {
                throw new RuntimeException("Seat already exists for this coach");
            }
        }
        existingSeat.setCoach(seat.getCoach());
        existingSeat.setSeatNumber(seat.getSeatNumber());
        existingSeat.setSeatType(seat.getSeatType());
        return seatRepository.save(existingSeat);
    }

    @Override
    public Seat deleteSeat(Long id) {
        Optional<Seat> oldSeat = seatRepository.findById(id);
        if(oldSeat.isPresent()){
            oldSeat.get().setActive(false);
            return seatRepository.save(oldSeat.get());
        }
        throw new RuntimeException("Not found");
    }

    @Override
    public List<Seat> getSeatsByCoach(Coach coach) {
        return seatRepository.findByCoach(coach);
    }
}
