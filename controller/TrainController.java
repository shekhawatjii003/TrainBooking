package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {
    private final TrainService trainService;
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
    @PostMapping
    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
        Train response = trainService.createTrain(train);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> response = trainService.getAllTrains();
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        Train response=trainService.getTrainById(id);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable Long id, @RequestBody Train train) {
        Train response = trainService.updateTrain(id, train);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Train> deleteTrain(@PathVariable Long id) {
        Train response = trainService.deleteTrain(id);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }
}
