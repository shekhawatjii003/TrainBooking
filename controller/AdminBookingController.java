package com.example.trainbooking.controller;

import com.example.trainbooking.dto.AdminBookingResponse;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.mapper.AdminBookingMapper;
import com.example.trainbooking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;
    private final AdminBookingMapper adminBookingMapper;

    public AdminBookingController(
            BookingService bookingService,
            AdminBookingMapper adminBookingMapper) {

        this.bookingService = bookingService;
        this.adminBookingMapper = adminBookingMapper;
    }
    @GetMapping
    public ResponseEntity<List<AdminBookingResponse>>
    getAllBookings() {

        List<Booking> bookings =
                bookingService.getAllBookings();

        List<AdminBookingResponse> response =
                bookings.stream()
                        .map(adminBookingMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminBookingResponse>
    getBookingById(@PathVariable Long id) {

        Booking booking =
                bookingService.getBookingById(id);

        return ResponseEntity.ok(
                adminBookingMapper.toResponse(booking)
        );
    }
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<AdminBookingResponse>
    getBookingByPnr(@PathVariable String pnr) {

        Booking booking =
                bookingService.getBookingByPnr(pnr);

        return ResponseEntity.ok(
                adminBookingMapper.toResponse(booking)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AdminBookingResponse>
    cancelBooking(@PathVariable Long id) {

        Booking cancelledBooking =
                bookingService.cancelBooking(id);

        return ResponseEntity.ok(
                adminBookingMapper.toResponse(
                        cancelledBooking
                )
        );
    }
}