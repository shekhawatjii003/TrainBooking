package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stations")
public class AdminStationController {

    private final StationService stationService;

    public AdminStationController(StationService stationService) {
        this.stationService = stationService;
    }

    // CREATE STATION
    @PostMapping
    public ResponseEntity<Station> createStation(
            @RequestBody Station station) {

        Station savedStation =
                stationService.createStation(station);

        return new ResponseEntity<>(
                savedStation,
                HttpStatus.CREATED
        );
    }

    // GET ALL STATIONS
    @GetMapping
    public ResponseEntity<List<Station>> getAllStations() {

        List<Station> stations =
                stationService.getAllStations();

        return ResponseEntity.ok(stations);
    }

    // GET STATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Station> getStationById(
            @PathVariable Long id) {

        Station station =
                stationService.getStationById(id);

        return ResponseEntity.ok(station);
    }

    // UPDATE STATION
    @PutMapping("/{id}")
    public ResponseEntity<Station> updateStation(
            @PathVariable Long id,
            @RequestBody Station station) {

        Station updatedStation =
                stationService.updateStation(id, station);

        return ResponseEntity.ok(updatedStation);
    }

    // DEACTIVATE STATION
    @DeleteMapping("/{id}")
    public ResponseEntity<Station> deleteStation(
            @PathVariable Long id) {

        Station station =
                stationService.deleteStation(id);

        return ResponseEntity.ok(station);
    }
}