package com.example.trainbooking.controller;

import com.example.trainbooking.entity.TrainStop;
import com.example.trainbooking.service.TrainStopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train-stops")
public class TrainStopController {
    private final TrainStopService trainStopService;
    public TrainStopController(TrainStopService trainStopService) {
        this.trainStopService = trainStopService;
    }
    @PostMapping
    public ResponseEntity<TrainStop> createtrainstop(@RequestBody TrainStop trainStop){
        TrainStop response=trainStopService.createTrainStop(trainStop);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/train/{trainid}")
    public ResponseEntity<List<TrainStop>> getTrainStopById(@PathVariable Long trainid){
        List<TrainStop> response=trainStopService.getTrainStopsByTrain(trainid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainStop> getTrainStopById2(@PathVariable Long id){
        TrainStop response=trainStopService.getTrainStopById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TrainStop> updateTrainStop(@PathVariable Long id, @RequestBody TrainStop trainStop){
        TrainStop response=trainStopService.updateTrainStop(id, trainStop);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TrainStop> deleteTrainStop(@PathVariable Long id){
        TrainStop response=trainStopService.deleteTrainStop(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
