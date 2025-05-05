package com.elusivemel.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elusivemel.paymentservice.PaymentStatus;
import com.elusivemel.paymentservice.dto.PaymentRequest;
import com.elusivemel.paymentservice.model.Payment;
import com.elusivemel.paymentservice.repository.PaymentRepository;
import com.elusivemel.paymentservice.util.ApprovalSelector;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment submitPayment(PaymentRequest paymentRequest) {

        Payment payment = new Payment();

        String approval = ApprovalSelector.pickOneThirdAsString(PaymentStatus.APPROVED, PaymentStatus.DECLINED);

        payment.setOrderId(paymentRequest.getOrderId());
        payment.setTotal(paymentRequest.getTotal());
        payment.setStatus(approval);

        Payment savedPayment = paymentRepository.save(payment);

        return savedPayment;
    }

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

}
