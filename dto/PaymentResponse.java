package com.example.trainbooking.dto;

import com.example.trainbooking.entity.Payment;
import com.example.trainbooking.entity.PaymentMethod;
import com.example.trainbooking.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Long bookingId;
    private String pnr;
    private Long userId;
    private String userName;
    private String userEmail;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDateTime;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());
        response.setBookingId(payment.getBooking().getId());
        response.setPnr(payment.getBooking().getPnr());
        response.setUserId(payment.getBooking().getUser().getId());
        response.setUserName(payment.getBooking().getUser().getName());
        response.setUserEmail(payment.getBooking().getUser().getEmail());
        response.setAmount(payment.getAmount());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setTransactionId(payment.getTransactionId());
        response.setPaymentDateTime(payment.getPaymentDateTime());

        return response;
    }
}
