package com.example.trainbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private String pnr;

    private Long bookingId;

    private Long trainNumber;

    private String trainName;

    private LocalDate journeyDate;

    private String source;

    private String destination;

    private List<PassengerTicketResponse> passengers;

    private BigDecimal totalFare;

    private String bookingStatus;
}