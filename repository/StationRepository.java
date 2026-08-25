package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByStationCode(String code);
    List<Station> findByCity(String city);
    List<Station> findByState(String state);
    List<Station> findByCityAndState(String city, String state);
    List<Station>findAllStationByActiveTrue();
    List<Station> findAllStationByActiveFalse();
}
