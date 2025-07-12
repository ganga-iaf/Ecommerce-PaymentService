package com.example.paymentservice.dtos;

import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private long orderId;
    private double amount;
    private PaymentStatus paymentStatus;

    public static PaymentResponseDto from(Payment payment) {
        PaymentResponseDto paymentResponseDto=new PaymentResponseDto();
        paymentResponseDto.setOrderId(payment.getOrderId());
        paymentResponseDto.setAmount(payment.getAmount());
        paymentResponseDto.setPaymentStatus(payment.getStatus());
        return paymentResponseDto;
    }
}
