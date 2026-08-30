package com.example.trainbooking.controller;

import com.example.trainbooking.entity.TrainStop;
import com.example.trainbooking.service.TrainStopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/train-stops")
public class AdminTrainStopController {

    private final TrainStopService trainStopService;

    public AdminTrainStopController(
            TrainStopService trainStopService) {

        this.trainStopService = trainStopService;
    }

    // CREATE TRAIN STOP
    @PostMapping
    public ResponseEntity<TrainStop> createTrainStop(
            @RequestBody TrainStop trainStop) {

        TrainStop savedTrainStop =
                trainStopService.createTrainStop(trainStop);

        return new ResponseEntity<>(
                savedTrainStop,
                HttpStatus.CREATED
        );
    }

    // GET ALL TRAIN STOPS
    @GetMapping
    public ResponseEntity<List<TrainStop>> getAllTrainStops() {

        List<TrainStop> trainStops =
                trainStopService.getAllTrainStops();

        return ResponseEntity.ok(trainStops);
    }

    // GET TRAIN STOP BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainStop> getTrainStopById(
            @PathVariable Long id) {

        TrainStop trainStop =
                trainStopService.getTrainStopById(id);

        return ResponseEntity.ok(trainStop);
    }

    // UPDATE TRAIN STOP
    @PutMapping("/{id}")
    public ResponseEntity<TrainStop> updateTrainStop(
            @PathVariable Long id,
            @RequestBody TrainStop trainStop) {

        TrainStop updatedTrainStop =
                trainStopService.updateTrainStop(
                        id,
                        trainStop
                );

        return ResponseEntity.ok(updatedTrainStop);
    }

    // DELETE / DEACTIVATE TRAIN STOP
    @DeleteMapping("/{id}")
    public ResponseEntity<TrainStop> deleteTrainStop(
            @PathVariable Long id) {

        TrainStop trainStop =
                trainStopService.deleteTrainStop(id);

        return ResponseEntity.ok(trainStop);
    }
}