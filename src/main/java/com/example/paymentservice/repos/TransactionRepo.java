package com.example.paymentservice.repos;

import com.example.paymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
