package com.example.trainbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerTicketResponse {

    private String name;

    private Integer age;

    private String gender;

    private String coachNumber;

    private Integer seatNumber;

    private String seatType;

    private String seatStatus;
}