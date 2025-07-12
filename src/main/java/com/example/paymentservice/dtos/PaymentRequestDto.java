package com.example.paymentservice.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentRequestDto {
    private long orderId;
    private List<OrderItemDto> orderItemDtos;
    private double amount;
}
