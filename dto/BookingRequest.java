package com.example.trainbooking.dto;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.entity.TrainJourney;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Train journey is required")
    private TrainJourney trainJourney;

    @NotNull(message = "Source station is required")
    private Station sourceStation;

    @NotNull(message = "Destination station is required")
    private Station destinationStation;

    @NotNull(message = "Total fare is required")
    @Positive(message = "Total fare must be greater than 0")
    private BigDecimal totalFare;
}