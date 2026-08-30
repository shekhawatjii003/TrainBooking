package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.service.CoachService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coaches")
public class AdminCoachController {

    private final CoachService coachService;

    public AdminCoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    // CREATE COACH
    @PostMapping
    public ResponseEntity<Coach> createCoach(
            @RequestBody Coach coach) {

        Coach savedCoach =
                coachService.createCoach(coach);

        return new ResponseEntity<>(
                savedCoach,
                HttpStatus.CREATED
        );
    }

    // GET ALL COACHES
    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {

        List<Coach> coaches =
                coachService.getCoaches();

        return ResponseEntity.ok(coaches);
    }

    // GET COACH BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoachById(
            @PathVariable Long id) {

        Coach coach =
                coachService.getCoachById(id);

        return ResponseEntity.ok(coach);
    }

    // UPDATE COACH
    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(
            @PathVariable Long id,
            @RequestBody Coach coach) {

        Coach updatedCoach =
                coachService.updateCoach(id, coach);

        return ResponseEntity.ok(updatedCoach);
    }

    // DELETE / DEACTIVATE COACH
    @DeleteMapping("/{id}")
    public ResponseEntity<Coach> deleteCoach(
            @PathVariable Long id) {

        Coach coach =
                coachService.deleteCoach(id);

        return ResponseEntity.ok(coach);
    }
}