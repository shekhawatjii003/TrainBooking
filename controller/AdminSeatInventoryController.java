package com.example.trainbooking.controller;

import com.example.trainbooking.entity.SeatInventory;
import com.example.trainbooking.service.SeatInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/seat-inventory")
public class AdminSeatInventoryController {

    private final SeatInventoryService seatInventoryService;

    public AdminSeatInventoryController(
            SeatInventoryService seatInventoryService) {

        this.seatInventoryService = seatInventoryService;
    }

    // CREATE SEAT INVENTORY
    @PostMapping
    public ResponseEntity<SeatInventory> createSeatInventory(
            @RequestBody SeatInventory seatInventory) {

        SeatInventory saved =
                seatInventoryService.createSeatInventory(
                        seatInventory
                );

        return new ResponseEntity<>(
                saved,
                HttpStatus.CREATED
        );
    }

    // GET ALL SEAT INVENTORY
    @GetMapping
    public ResponseEntity<List<SeatInventory>> getAllSeatInventory() {

        List<SeatInventory> inventory =
                seatInventoryService.getAllSeatInventories();

        return ResponseEntity.ok(inventory);
    }

    // GET SEAT INVENTORY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SeatInventory> getSeatInventoryById(
            @PathVariable Long id) {

        SeatInventory inventory =
                seatInventoryService.getSeatInventoryById(id);

        return ResponseEntity.ok(inventory);
    }

    // UPDATE SEAT INVENTORY
    @PutMapping("/{id}")
    public ResponseEntity<SeatInventory> updateSeatInventory(
            @PathVariable Long id,
            @RequestBody SeatInventory seatInventory) {

        SeatInventory updated =
                seatInventoryService.updateSeatInventory(
                        id,
                        seatInventory
                );

        return ResponseEntity.ok(updated);
    }

    // DEACTIVATE SEAT INVENTORY
    @DeleteMapping("/{id}")
    public ResponseEntity<SeatInventory> deleteSeatInventory(
            @PathVariable Long id) {

        SeatInventory inventory =
                seatInventoryService.deleteSeatInventory(id);

        return ResponseEntity.ok(inventory);
    }
}