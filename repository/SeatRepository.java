package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByCoach(Coach coach);
    Boolean existsByCoachAndSeatNumber(Coach coach, Integer seatNumber);
}
