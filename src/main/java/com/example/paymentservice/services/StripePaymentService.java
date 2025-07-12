package com.example.paymentservice.services;

import com.example.paymentservice.dtos.OrderItemDto;
import com.stripe.exception.StripeException;

import java.util.List;
import java.util.Map;

public interface StripePaymentService {
    Map<String,String> createCheckoutSession(long orderId, List<OrderItemDto> orderItemDtos) throws StripeException;
    String getPaymentStatus(String sessionId) throws StripeException;
}
