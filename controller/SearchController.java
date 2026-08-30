package com.example.trainbooking.controller;

import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/search/trains")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<TrainJourney>> searchTrains(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam LocalDate date) {

        List<TrainJourney> response =
                searchService.searchTrains(from, to, date);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}