package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking>findByPnr(String pnr);
    List<Booking> findByUser(User user);
    List<Booking>findByTrainJourney(TrainJourney trainJourney);



}
