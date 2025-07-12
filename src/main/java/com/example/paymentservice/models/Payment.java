package com.example.paymentservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
     private long orderId;
     private double amount;
     private String currency="INR";
     private Date paidDate;
     private PaymentStatus status;
     private String gatewaySessionId;
     @OneToMany(cascade = CascadeType.ALL,mappedBy = "payment")
     private List<Transaction> transactions;

}
