package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.PaymentRequestDto;
import com.example.paymentservice.dtos.PaymentResponseDto;
import com.example.paymentservice.models.Payment;
import com.example.paymentservice.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
         try {
             String paymentLink = paymentService.processPayment(paymentRequestDto.getOrderId(), paymentRequestDto.getAmount(), paymentRequestDto.getOrderItemDtos());
             return new ResponseEntity<>(paymentLink, HttpStatus.OK);
         }catch (Exception e){
             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    @GetMapping("/status/{session_id}")
    public ResponseEntity<PaymentResponseDto> getPaymentStatus(@PathVariable("session_id") String sessionId) throws StripeException {
          Payment payment=paymentService.checkPaymentStatus(sessionId);
          return new ResponseEntity<>(PaymentResponseDto.from(payment), HttpStatus.OK);
    }
}
