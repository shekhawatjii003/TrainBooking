package com.example.trainbooking.repository;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBooking(Booking booking);
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByTransactionId(String transactionId);
    boolean existsByBooking(Booking booking);

    @Query("""
           SELECT p
           FROM Payment p
           WHERE p.booking.user.email = :email
           """)
    List<Payment> findPaymentsByUserEmail(@Param("email") String email);

}
