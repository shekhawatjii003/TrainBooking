package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBooking(Booking booking);
    Optional<Payment> findByTransactionId(String transactionId);

}
