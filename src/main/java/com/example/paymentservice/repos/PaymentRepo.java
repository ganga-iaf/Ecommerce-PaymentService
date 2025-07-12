package com.example.paymentservice.repos;

import com.example.paymentservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
    //@Query(value = "select * from payment where gateway_session_id=:gatewaySessionId",nativeQuery = true)
    Optional<Payment> findByGatewaySessionId(String gatewaySessionId);
}
