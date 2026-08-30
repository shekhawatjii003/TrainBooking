package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Seat;
import com.example.trainbooking.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/seats")
public class AdminSeatController {

    private final SeatService seatService;

    public AdminSeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // CREATE SEAT
    @PostMapping
    public ResponseEntity<Seat> createSeat(
            @RequestBody Seat seat) {

        Seat savedSeat =
                seatService.createSeat(seat);

        return new ResponseEntity<>(
                savedSeat,
                HttpStatus.CREATED
        );
    }

    // GET ALL SEATS
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {

        List<Seat> seats =
                seatService.getAllSeats();

        return ResponseEntity.ok(seats);
    }

    // GET SEAT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(
            @PathVariable Long id) {

        Seat seat =
                seatService.getSeatById(id);

        return ResponseEntity.ok(seat);
    }

    // UPDATE SEAT
    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(
            @PathVariable Long id,
            @RequestBody Seat seat) {

        Seat updatedSeat =
                seatService.updateSeat(id, seat);

        return ResponseEntity.ok(updatedSeat);
    }

    // DELETE / DEACTIVATE SEAT
    @DeleteMapping("/{id}")
    public ResponseEntity<Seat> deleteSeat(
            @PathVariable Long id) {

        Seat seat =
                seatService.deleteSeat(id);

        return ResponseEntity.ok(seat);
    }
}