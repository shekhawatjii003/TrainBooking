package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    private final StationService stationService;
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }
    @PostMapping
    public ResponseEntity<Station> addStation(@RequestBody Station station) {
        Station response=stationService.createStation(station);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Station>> getAllStations() {
        List<Station> response=stationService.getAllStations();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Station> getStationById(@PathVariable Long id) {
        Station response=stationService.getStationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable Long id, @RequestBody Station station) {
        Station response=stationService.updateStation(id,station);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Station> deleteStationById(@PathVariable Long id) {
        Station response=stationService.deleteStation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
