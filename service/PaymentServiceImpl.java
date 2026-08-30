package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Payment;
import com.example.trainbooking.entity.PaymentStatus;
import com.example.trainbooking.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.DuplicateResourceException;
import com.example.trainbooking.exception.InvalidBookingStateException;
import com.example.trainbooking.exception.InvalidRequestException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            BookingService bookingService) {

        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
    }

    @Override
    public Payment createPayment(Payment payment) {

        if (paymentRepository.existsByBooking(
                payment.getBooking())) {

            throw new DuplicateResourceException(
                    "Booking already exists"
            );
        }

        if (paymentRepository.existsByTransactionId(
                payment.getTransactionId())) {

            throw new DuplicateResourceException(
                    "Payment already exists"
            );
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long id) {

        Optional<Payment> payment =
                paymentRepository.findById(id);

        if (payment.isPresent()) {
            return payment.get();
        }

        throw new ResourceNotFoundException(
                "Payment not found"
        );
    }

    @Override
    public Payment updatePayment(
            Long id,
            Payment payment) {

        Optional<Payment> oldPayment =
                paymentRepository.findById(id);

        if (!oldPayment.isPresent()) {

            throw new ResourceNotFoundException(
                    "Payment not found"
            );
        }

        if (!oldPayment.get()
                .getBooking()
                .equals(payment.getBooking())) {

            throw new InvalidRequestException(
                    "Booking can not be updated"
            );
        }

        oldPayment.get().setPaymentMethod(
                payment.getPaymentMethod()
        );

        oldPayment.get().setPaymentStatus(
                payment.getPaymentStatus()
        );

        return paymentRepository.save(
                oldPayment.get()
        );
    }

    @Override
    public Payment refundPayment(Long id) {

        Optional<Payment> paymentOptional =
                paymentRepository.findById(id);

        if (!paymentOptional.isPresent()) {

            throw new ResourceNotFoundException(
                    "Payment not found"
            );
        }

        Payment payment = paymentOptional.get();

        // Payment must be successful
        if (payment.getPaymentStatus()
                != PaymentStatus.SUCCESS) {

            throw new InvalidBookingStateException(
                    "Only successful payment can be refunded"
            );
        }

        // First cancel booking.
        // Existing BookingService will handle
        // booking cancellation and seat release.
        Booking booking = payment.getBooking();

        bookingService.cancelBooking(
                booking.getId()
        );

        // Now refund payment
        payment.setPaymentStatus(
                PaymentStatus.REFUNDED
        );

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentByBooking(
            Booking booking) {

        List<Payment> payments =
                paymentRepository.findByBooking(booking);

        if (payments.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Payment not found"
            );
        }

        return payments;
    }

    @Override
    public List<Payment> getAllPayments() {

        List<Payment> payments =
                paymentRepository.findAll();

        if (payments.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Payment not found"
            );
        }

        return payments;
    }
}