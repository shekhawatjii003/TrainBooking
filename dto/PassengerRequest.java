package com.example.trainbooking.dto;

import com.example.trainbooking.entity.Gender;
import com.example.trainbooking.entity.SeatInventory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @NotNull(message = "Passenger age is required")
    @Min(value = 1, message = "Passenger age must be greater than 0")
    @Max(value = 120, message = "Passenger age must be less than or equal to 120")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Seat inventory is required")
    private SeatInventory seatInventory;
}