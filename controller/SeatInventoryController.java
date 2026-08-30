package com.example.trainbooking.controller;
import com.example.trainbooking.entity.SeatInventory;
import com.example.trainbooking.entity.SeatStatus;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.service.SeatInventoryService;
import com.example.trainbooking.service.TrainJourneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-inventory")
public class SeatInventoryController {
    private final SeatInventoryService seatInventoryService;
    private final TrainJourneyService trainJourneyService;
    public SeatInventoryController(SeatInventoryService seatInventoryService, TrainJourneyService trainJourneyService) {
        this.seatInventoryService = seatInventoryService;
        this.trainJourneyService = trainJourneyService;
    }
    @PostMapping
    public ResponseEntity<SeatInventory> createSeatInventory(@RequestBody SeatInventory seatInventory) {
        SeatInventory response=seatInventoryService.createSeatInventory(seatInventory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<SeatInventory>> findAllSeatInventory() {
        List<SeatInventory>response=seatInventoryService.getAllSeatInventories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SeatInventory> findSeatInventoryById(@PathVariable Long id) {
        SeatInventory response=seatInventoryService.getSeatInventoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SeatInventory>updateSeatInventory(@PathVariable Long id, @RequestBody SeatInventory seatInventory) {
        SeatInventory response=seatInventoryService.updateSeatInventory(id, seatInventory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<SeatInventory> deleteSeatInventory(@PathVariable Long id) {
        SeatInventory response=seatInventoryService.deleteSeatInventory(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/journey/{journeyId}")
    public ResponseEntity<List<SeatInventory>> getseatInventoriesByJourneyId(
            @PathVariable Long journeyId) {

        TrainJourney trainJourney = trainJourneyService.getTrainJourneyById(journeyId);

        List<SeatInventory> response =
                seatInventoryService.getInventoryByTrainJourney(trainJourney);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/journey/{journeyId}/available")
    public ResponseEntity<List<SeatInventory>> getAvailableSeats(
            @PathVariable Long journeyId) {

        TrainJourney trainJourney =
                trainJourneyService.getTrainJourneyById(journeyId);

        List<SeatInventory> response =
                seatInventoryService.getInventoryByJourneyAndStatus(
                        trainJourney,
                        SeatStatus.AVAILABLE
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
