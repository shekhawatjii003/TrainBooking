package com.example.trainbooking.controller;

import com.example.trainbooking.dto.PaymentResponse;
import com.example.trainbooking.entity.Payment;
import com.example.trainbooking.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long id) {

        Payment payment = paymentService.getPayment(id);

        return ResponseEntity.ok(PaymentResponse.from(payment));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPayments() {

        List<PaymentResponse> payments = paymentService.getAllPayments()
                .stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long id) {

        Payment payment = paymentService.refundPayment(id);

        return ResponseEntity.ok(PaymentResponse.from(payment));
    }
}
