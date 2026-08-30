package com.example.trainbooking.mapper;

import com.example.trainbooking.dto.BookingRequest;
import com.example.trainbooking.dto.PassengerRequest;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Booking convertBookingRequest(BookingRequest request) {

        Booking booking = new Booking();
        booking.setSourceStation(
                request.getSourceStation()
        );
        booking.setDestinationStation(
                request.getDestinationStation()
        );
        booking.setTotalFare(
                request.getTotalFare()
        );
        booking.setTrainJourney(
                request.getTrainJourney()
        );
        return booking;
    }

    public Passenger convertPassengerRequest(
            PassengerRequest request) {
        Passenger passenger = new Passenger();
        passenger.setName(
                request.getName()
        );
        passenger.setAge(
                request.getAge()
        );
        passenger.setGender(
                request.getGender()
        );
        passenger.setSeatInventory(
                request.getSeatInventory()
        );
        return passenger;
    }
}