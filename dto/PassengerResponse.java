package com.example.trainbooking.dto;

import com.example.trainbooking.entity.Gender;
import com.example.trainbooking.entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponse {

    private Long id;
    private Long bookingId;
    private String pnr;
    private String name;
    private Integer age;
    private Gender gender;
    private Long seatInventoryId;
    private Boolean active;

    public static PassengerResponse from(Passenger passenger) {
        PassengerResponse response = new PassengerResponse();

        response.setId(passenger.getId());
        response.setBookingId(passenger.getBooking().getId());
        response.setPnr(passenger.getBooking().getPnr());
        response.setName(passenger.getName());
        response.setAge(passenger.getAge());
        response.setGender(passenger.getGender());
        response.setSeatInventoryId(passenger.getSeatInventory().getId());
        response.setActive(passenger.getActive());

        return response;
    }
}
