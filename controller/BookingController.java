package com.example.trainbooking.controller;

import com.example.trainbooking.dto.BookingResponse;
import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.exception.UnauthorizedException;
import com.example.trainbooking.service.BookingService;
import com.example.trainbooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(
            BookingService bookingService,
            UserService userService) {

        this.bookingService = bookingService;
        this.userService = userService;
    }

    // Get currently logged-in user from the JWT
    private User getLoggedInUser() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    // Get my bookings
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        User user = getLoggedInUser();

        List<BookingResponse> bookings =
                bookingService.getBookingsByUser(user)
                        .stream()
                        .map(BookingResponse::from)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    // Get a single booking - ADMIN can view any, USER only their own
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Long id) {

        User user = getLoggedInUser();

        Booking booking = bookingService.getBookingById(id);

        boolean isAdmin = "ADMIN".equals(user.getRole());
        boolean isOwner = booking.getUser().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new UnauthorizedException(
                    "You are not authorized to view this booking"
            );
        }

        return ResponseEntity.ok(BookingResponse.from(booking));
    }

    // Get all bookings - ADMIN only
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        User user = getLoggedInUser();

        if (!"ADMIN".equals(user.getRole())) {
            throw new UnauthorizedException(
                    "You are not authorized to view all bookings"
            );
        }

        List<BookingResponse> bookings =
                bookingService.getAllBookings()
                        .stream()
                        .map(BookingResponse::from)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id) {

        User user = getLoggedInUser();

        Booking booking = bookingService.getBookingById(id);

        boolean isAdmin = "ADMIN".equals(user.getRole());
        boolean isOwner = booking.getUser().getId().equals(user.getId());

        // Check ownership (ADMIN can cancel any booking)
        if (!isAdmin && !isOwner) {
            throw new UnauthorizedException(
                    "You are not authorized to cancel this booking"
            );
        }

        Booking cancelledBooking =
                bookingService.cancelBooking(id);

        return ResponseEntity.ok(BookingResponse.from(cancelledBooking));
    }
}
