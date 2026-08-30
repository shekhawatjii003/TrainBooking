package com.example.trainbooking.controller;

import com.example.trainbooking.entity.Coach;
import com.example.trainbooking.service.CoachService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {
    private final CoachService coachService;
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }
    @PostMapping
    public ResponseEntity<Coach> addCoach(@RequestBody Coach coach){
        Coach response=coachService.createCoach(coach);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches(){
        List<Coach> response=coachService.getCoaches();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable Long id){
        Coach response=coachService.getCoachById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach coach){
        Coach response=coachService.updateCoach(id, coach);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Coach> deleteCoach(@PathVariable Long id){
        Coach response=coachService.deleteCoach(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
