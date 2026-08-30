package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.service.TrainJourneyService;
import com.example.trainbooking.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/train-journeys")
public class TrainJourneyController {
    private final TrainJourneyService trainJourneyService;
    private final TrainService trainService;
    public TrainJourneyController(TrainJourneyService trainJourneyService,TrainService trainService) {
        this.trainJourneyService = trainJourneyService;
        this.trainService=trainService;
    }
    @PostMapping
    public ResponseEntity<TrainJourney> createnewjourney(@RequestBody TrainJourney trainJourney)
    {
        TrainJourney response=trainJourneyService.createTrainJourney(trainJourney);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TrainJourney>> getAllTrainJourney()
    {
        List<TrainJourney>response=trainJourneyService.getAllTrainJourneys();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainJourney> gettrainjourney(@PathVariable Long id)
    {
        TrainJourney response=trainJourneyService.getTrainJourneyById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TrainJourney>updateTrainJourney(@PathVariable Long id, @RequestBody TrainJourney trainJourney){
        TrainJourney response=trainJourneyService.updateTrainJourney(id, trainJourney);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TrainJourney> deletetrainjourney(@PathVariable Long id)
    {
        TrainJourney response=trainJourneyService.deleteTrainJourney(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<TrainJourney> getJourneyByTrainAndDate(
            @RequestParam Long trainId,
            @RequestParam LocalDate journeyDate) {

        Train train = trainService.getTrainById(trainId);

        TrainJourney response =
                trainJourneyService.getJourneyByTrainAndDate(train, journeyDate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
