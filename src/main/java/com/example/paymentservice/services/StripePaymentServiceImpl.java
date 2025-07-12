package com.example.paymentservice.services;

import com.example.paymentservice.dtos.OrderItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    @PostConstruct
    public void Init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public Map<String,String> createCheckoutSession(long orderId,List<OrderItemDto> orderItemDtos) throws StripeException {

        List<SessionCreateParams.LineItem> lineItems= orderItemDtos.stream().map(orderItemDto ->
        SessionCreateParams.LineItem.builder().setQuantity((long)orderItemDto.getQuantity())
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("inr")
                        .setUnitAmount((long)orderItemDto.getUnitPrice()*100)
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(orderItemDto.getProductName()).build()).build()).build()
        ).toList();

        SessionCreateParams params=SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.google.co.in/search?q=success&session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://www.google.co.in/search?q=failure&session_id={CHECKOUT_SESSION_ID}")
                .addAllLineItem(lineItems)
                .setClientReferenceId(Long.toString(orderId))
                .build();
        Map<String,String> responseMap=new HashMap<>();
        Session session= Session.create(params);
           responseMap.put("session_id",session.getId());
           responseMap.put("url",session.getUrl());
        return responseMap;
    }

    @Override
    public String getPaymentStatus(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        if ("complete".equals(session.getStatus())) {
            String paymentIntentId = session.getPaymentIntent();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return  paymentIntent.getStatus();
        }
        return "processing";
    }
}
