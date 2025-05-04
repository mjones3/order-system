package com.elusivemel.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elusivemel.paymentservice.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
