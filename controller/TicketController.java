package com.example.trainbooking.controller;

import com.example.trainbooking.dto.TicketResponse;
import com.example.trainbooking.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<TicketResponse> getTicketByPnr(
            @PathVariable String pnr) {

        TicketResponse response =
                ticketService.getTicketByPnr(pnr);

        return ResponseEntity.ok(response);
    }
}