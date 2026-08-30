package com.example.trainbooking.mapper;

import com.example.trainbooking.dto.AdminBookingResponse;
import com.example.trainbooking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class AdminBookingMapper {

    public AdminBookingResponse toResponse(Booking booking) {

        AdminBookingResponse response =
                new AdminBookingResponse();

        response.setId(booking.getId());
        response.setPnr(booking.getPnr());

        // User
        if (booking.getUser() != null) {
            response.setUserId(
                    booking.getUser().getId()
            );

            response.setUserName(
                    booking.getUser().getName()
            );

            response.setUserEmail(
                    booking.getUser().getEmail()
            );
        }

        // Train Journey
        if (booking.getTrainJourney() != null) {

            response.setTrainJourneyId(
                    booking.getTrainJourney().getId()
            );

            if (booking.getTrainJourney().getTrain() != null) {

                response.setTrainNumber(
                        booking.getTrainJourney()
                                .getTrain()
                                .getTrainNumber()
                );

                response.setTrainName(
                        booking.getTrainJourney()
                                .getTrain()
                                .getTrainName()
                );
            }
        }

        // Stations
        if (booking.getSourceStation() != null) {

            response.setSourceStation(
                    booking.getSourceStation()
                            .getStationCode()
            );
        }

        if (booking.getDestinationStation() != null) {

            response.setDestinationStation(
                    booking.getDestinationStation()
                            .getStationCode()
            );
        }

        response.setBookingDateTime(
                booking.getBookingDateTime()
        );

        response.setBookingStatus(
                booking.getBookingStatus()
        );

        response.setTotalFare(
                booking.getTotalFare()
        );

        return response;
    }
}