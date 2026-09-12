package com.example.trainbooking.dto;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private String pnr;

    private Long userId;
    private String userName;
    private String userEmail;

    private Long trainJourneyId;
    private Long trainNumber;
    private String trainName;
    private LocalDate journeyDate;

    private Long sourceStationId;
    private String sourceStationCode;
    private String sourceStationName;

    private Long destinationStationId;
    private String destinationStationCode;
    private String destinationStationName;

    private LocalDateTime bookingDateTime;
    private BookingStatus bookingStatus;
    private BigDecimal totalFare;

    public static BookingResponse from(Booking booking) {
        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setPnr(booking.getPnr());

        response.setUserId(booking.getUser().getId());
        response.setUserName(booking.getUser().getName());
        response.setUserEmail(booking.getUser().getEmail());

        response.setTrainJourneyId(booking.getTrainJourney().getId());
        response.setTrainNumber(booking.getTrainJourney().getTrain().getTrainNumber());
        response.setTrainName(booking.getTrainJourney().getTrain().getTrainName());
        response.setJourneyDate(booking.getTrainJourney().getJourneyDate());

        response.setSourceStationId(booking.getSourceStation().getId());
        response.setSourceStationCode(booking.getSourceStation().getStationCode());
        response.setSourceStationName(booking.getSourceStation().getStationName());

        response.setDestinationStationId(booking.getDestinationStation().getId());
        response.setDestinationStationCode(booking.getDestinationStation().getStationCode());
        response.setDestinationStationName(booking.getDestinationStation().getStationName());

        response.setBookingDateTime(booking.getBookingDateTime());
        response.setBookingStatus(booking.getBookingStatus());
        response.setTotalFare(booking.getTotalFare());

        return response;
    }
}
