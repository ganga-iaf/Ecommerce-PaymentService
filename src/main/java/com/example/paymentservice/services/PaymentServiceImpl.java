package com.example.paymentservice.services;

import com.example.paymentservice.dtos.OrderItemDto;
import com.example.paymentservice.dtos.PaymentResponseDto;
import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentStatus;
import com.example.paymentservice.repos.PaymentRepo;
import com.example.paymentservice.repos.TransactionRepo;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private StripePaymentService stripePaymentService;

    @Override
    public String processPayment(long orderId, double amount, List<OrderItemDto> orderItemDtos) throws StripeException {
        Payment payment=new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setCurrency("INR");
        payment.setStatus(PaymentStatus.Initiated);
        paymentRepo.save(payment);
        Map<String,String> stripeSession=stripePaymentService.createCheckoutSession(orderId,orderItemDtos);
        payment.setGatewaySessionId(stripeSession.get("session_id"));
        paymentRepo.save(payment);
        return stripeSession.get("url");
    }

    @Override
    public Payment checkPaymentStatus(String sessionId) throws StripeException {
        String status=stripePaymentService.getPaymentStatus(sessionId);
        Optional<Payment> paymentOptional=paymentRepo.findByGatewaySessionId(sessionId);
        if(paymentOptional.isEmpty()){
            throw new RuntimeException("Payment not found");
        }
        Payment payment=paymentOptional.get();
        if("succeeded".equalsIgnoreCase(status)){
            payment.setStatus(PaymentStatus.Completed);
        }
        else if("failed".equalsIgnoreCase(status)){
            payment.setStatus(PaymentStatus.Failed);
        }  else if("processing".equalsIgnoreCase(status)){
            payment.setStatus(PaymentStatus.Processing);
        } else{
            payment.setStatus(PaymentStatus.Cancelled);
        }
        paymentRepo.save(payment);
        return payment;
    }
}
