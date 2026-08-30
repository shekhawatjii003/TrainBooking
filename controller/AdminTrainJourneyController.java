package com.example.trainbooking.controller;

import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.service.TrainJourneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/train-journeys")
public class AdminTrainJourneyController {

    private final TrainJourneyService trainJourneyService;

    public AdminTrainJourneyController(
            TrainJourneyService trainJourneyService) {

        this.trainJourneyService = trainJourneyService;
    }

    // CREATE TRAIN JOURNEY
    @PostMapping
    public ResponseEntity<TrainJourney> createTrainJourney(
            @RequestBody TrainJourney trainJourney) {

        TrainJourney savedJourney =
                trainJourneyService.createTrainJourney(trainJourney);

        return new ResponseEntity<>(
                savedJourney,
                HttpStatus.CREATED
        );
    }

    // GET ALL TRAIN JOURNEYS
    @GetMapping
    public ResponseEntity<List<TrainJourney>> getAllTrainJourneys() {

        List<TrainJourney> journeys =
                trainJourneyService.getAllTrainJourneys();

        return ResponseEntity.ok(journeys);
    }

    // GET TRAIN JOURNEY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainJourney> getTrainJourneyById(
            @PathVariable Long id) {

        TrainJourney journey =
                trainJourneyService.getTrainJourneyById(id);

        return ResponseEntity.ok(journey);
    }

    // UPDATE TRAIN JOURNEY
    @PutMapping("/{id}")
    public ResponseEntity<TrainJourney> updateTrainJourney(
            @PathVariable Long id,
            @RequestBody TrainJourney trainJourney) {

        TrainJourney updatedJourney =
                trainJourneyService.updateTrainJourney(
                        id,
                        trainJourney
                );

        return ResponseEntity.ok(updatedJourney);
    }

    // DEACTIVATE TRAIN JOURNEY
    @DeleteMapping("/{id}")
    public ResponseEntity<TrainJourney> deleteTrainJourney(
            @PathVariable Long id) {

        TrainJourney journey =
                trainJourneyService.deleteTrainJourney(id);

        return ResponseEntity.ok(journey);
    }
}