package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.BookingStatus;
import com.example.trainbooking.entity.Payment;
import com.example.trainbooking.entity.PaymentStatus;
import com.example.trainbooking.entity.User;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.UnauthorizedException;
import com.example.trainbooking.repository.PaymentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final BookingService bookingService;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            UserService userService,
            BookingService bookingService) {

        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.bookingService = bookingService;
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

    @Override
    public Payment createPayment(Payment payment) {
        if (paymentRepository.existsByBooking(payment.getBooking())) {
            throw new RuntimeException("Booking already exists");
        }
        if (paymentRepository.existsByTransactionId(payment.getTransactionId())) {
            throw new RuntimeException("Payment already exists");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found")
                );

        User loggedInUser = getLoggedInUser();

        boolean isAdmin = "ADMIN".equals(loggedInUser.getRole());
        boolean isOwner = payment.getBooking().getUser().getId()
                .equals(loggedInUser.getId());

        if (!isAdmin && !isOwner) {
            throw new UnauthorizedException(
                    "You are not authorized to view this payment"
            );
        }

        return payment;
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Optional<Payment> oldPayment = paymentRepository.findById(id);
        if (!oldPayment.isPresent()) {
            throw new ResourceNotFoundException("Payment not found");
        }
        if (!oldPayment.get().getBooking().equals(payment.getBooking())) {
            throw new RuntimeException("Booking can not be updated");
        }
        oldPayment.get().setPaymentMethod(payment.getPaymentMethod());
        oldPayment.get().setPaymentStatus(payment.getPaymentStatus());
        return paymentRepository.save(oldPayment.get());
    }

    @Override
    @Transactional
    public Payment refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found")
                );

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException(
                    "Only successful payment can be refunded"
            );
        }

        User loggedInUser = getLoggedInUser();

        boolean isAdmin = "ADMIN".equals(loggedInUser.getRole());
        boolean isOwner = payment.getBooking().getUser().getId()
                .equals(loggedInUser.getId());

        if (!isAdmin && !isOwner) {
            throw new UnauthorizedException(
                    "You are not authorized to refund this payment"
            );
        }

        Booking booking = payment.getBooking();

        // Avoid re-cancelling an already cancelled booking (previously caused a 409)
        if (booking.getBookingStatus() != BookingStatus.CANCELLED) {
            bookingService.cancelBooking(booking.getId());
        }

        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentByBooking(Booking booking) {
        List<Payment> payment = paymentRepository.findByBooking(booking);
        if (!payment.isEmpty()) {
            return payment;
        }
        throw new ResourceNotFoundException("Payment not found");
    }

    @Override
    public List<Payment> getAllPayments() {
        User loggedInUser = getLoggedInUser();

        List<Payment> payments;

        if ("ADMIN".equals(loggedInUser.getRole())) {
            payments = paymentRepository.findAll();
        } else {
            payments = paymentRepository
                    .findPaymentsByUserEmail(loggedInUser.getEmail());
        }

        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found");
        }

        return payments;
    }
}
