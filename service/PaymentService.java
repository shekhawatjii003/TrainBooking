package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPayment(Long id);
    Payment updatePayment(Long id,Payment payment);
    Payment refundPayment(Long id);
    List<Payment> getPaymentByBooking(Booking booking);
    List<Payment> getAllPayments();
}
