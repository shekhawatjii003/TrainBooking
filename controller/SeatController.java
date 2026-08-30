package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    private final SeatService seatService;
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }
    @PostMapping
    public ResponseEntity<Seat>createSeat(@RequestBody Seat seat){
        Seat response=seatService.createSeat(seat);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats(){
        List<Seat> response=seatService.getAllSeats();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id){
        Seat response=seatService.getSeatById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody Seat seat){
        Seat response=seatService.updateSeat(id, seat);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Seat> deleteSeat(@PathVariable Long id){
        Seat response=seatService.deleteSeat(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
