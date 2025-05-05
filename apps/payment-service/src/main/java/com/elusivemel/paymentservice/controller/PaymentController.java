package com.elusivemel.paymentservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elusivemel.paymentservice.PaymentStatus;
import com.elusivemel.paymentservice.dto.PaymentRequest;
import com.elusivemel.paymentservice.dto.PaymentResponse;
import com.elusivemel.paymentservice.model.Payment;
import com.elusivemel.paymentservice.repository.PaymentRepository;
import com.elusivemel.paymentservice.util.ApprovalSelector;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> submitPayment(@RequestBody PaymentRequest paymentRequest) {

        logger.info("Recieved payment request: {}", paymentRequest);

        Payment payment = new Payment();

        String approval = ApprovalSelector.pickOneThirdAsString(PaymentStatus.APPROVED, PaymentStatus.DECLINED);

        payment.setOrderId(paymentRequest.getOrderId());
        payment.setTotal(paymentRequest.getTotal());
        payment.setStatus(approval);

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse(savedPayment);

        if (savedPayment.getStatus().equals(PaymentStatus.DECLINED.getValue())) {
            logger.info("Payment for orderId = {} not approved.", savedPayment.getOrderId());
            return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);

        } else {
            logger.info("Payment response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
