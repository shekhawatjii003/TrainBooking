package com.example.trainbooking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationBookingRequest {

    @Valid
    @NotNull
    private BookingRequest booking;

    @Valid
    @NotEmpty
    private List<PassengerRequest> passengers;
}