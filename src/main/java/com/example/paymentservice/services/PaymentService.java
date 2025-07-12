package com.example.paymentservice.services;

import com.example.paymentservice.dtos.OrderItemDto;
import com.example.paymentservice.dtos.PaymentRequestDto;
import com.example.paymentservice.dtos.PaymentResponseDto;
import com.example.paymentservice.models.Payment;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PaymentService {
    String processPayment(long orderId, double amount, List<OrderItemDto> orderItemDtos) throws StripeException;
    Payment checkPaymentStatus(String sessionId) throws StripeException;
}
