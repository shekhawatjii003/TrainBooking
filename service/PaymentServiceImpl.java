package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Payment;
import com.example.trainbooking.entity.PaymentStatus;
import com.example.trainbooking.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment payment) {
        if(paymentRepository.existsByBooking(payment.getBooking())){
            throw new RuntimeException("Booking already exists");
        }
        if(paymentRepository.existsByTransactionId(payment.getTransactionId())){
            throw new RuntimeException("Payment already exists");
        }
        return paymentRepository.save(payment);

    }

    @Override
    public Payment getPayment(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()){
            return payment.get();
        }
        throw new RuntimeException("Payment not found");
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Optional<Payment>oldPayment = paymentRepository.findById(id);
        if(!oldPayment.isPresent()){
            throw new RuntimeException("Payment not found");
        }
        if(!oldPayment.get().getBooking().equals(payment.getBooking())){
            throw new RuntimeException("Booking can not be updated");
        }
        oldPayment.get().setPaymentMethod(payment.getPaymentMethod());
        oldPayment.get().setPaymentStatus(payment.getPaymentStatus());
        return paymentRepository.save(oldPayment.get());
    }

    @Override
    public Payment refundPayment(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()){
            Payment pay = payment.get();
            if (pay.getPaymentStatus() != PaymentStatus.SUCCESS) {
                throw new RuntimeException("Only successful payment can be refunded");
            }
            pay.setPaymentStatus(PaymentStatus.REFUNDED);
            return paymentRepository.save(pay);
        }
        throw new RuntimeException("Payment not found");
    }

    @Override
    public List<Payment> getPaymentByBooking(Booking booking) {
        List<Payment> payment=paymentRepository.findByBooking(booking);
       if(!payment.isEmpty()){
           return payment;
       }
        throw new RuntimeException("Payment not found");
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        if(payments.isEmpty()){
            throw new RuntimeException("Payment not found");
        }
        return payments;
    }
}
