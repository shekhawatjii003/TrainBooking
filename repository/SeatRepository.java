package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByCoach(Coach coach);
    Boolean existsByCoachAndSeatNumber(Coach coach, Integer seatNumber);
    Optional<Seat> findByCoachAndSeatNumber(
            Coach coach,
            Integer seatNumber
    );

    boolean existsByCoachAndSeatNumberAndIdNot(
            Coach coach,
            Integer seatNumber,
            Long id
    );
}
