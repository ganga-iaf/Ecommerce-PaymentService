package com.example.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Transaction extends BaseModel {
    private double amount;
    private String referenceId;
    private String gatewayResponse;
    private TransactionStatus status;
    private Date transactionAt;
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
