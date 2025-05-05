package com.elusivemel.paymentservice.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.elusivemel.paymentservice.dto.PaymentResponseItem;
import com.elusivemel.paymentservice.dto.ReleaseInventoryRequest;
import com.elusivemel.paymentservice.model.Payment;
import com.elusivemel.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Object> submitPayment(@RequestBody PaymentRequest paymentRequest) {

        logger.info("Recieved payment request: {}", paymentRequest);

        Payment payment = paymentService.submitPayment(paymentRequest);
        PaymentResponse response = new PaymentResponse(payment);

        List<PaymentResponseItem> responseItems = paymentRequest.getItems().stream()
                // either with a method reference:
                .map(PaymentResponseItem::new)
                // or explicitly:
                //.map(reqItem -> new PaymentResponseItem(reqItem))
                .collect(Collectors.toList());
        response.setItems(responseItems);

        if (response.getApproved().equals(PaymentStatus.DECLINED.getValue())) {
            logger.info("Payment for orderId = {} not approved.", payment.getOrderId());

            ReleaseInventoryRequest releaseInventoryRequest = new ReleaseInventoryRequest(paymentRequest);

            return new ResponseEntity<>(releaseInventoryRequest, HttpStatus.PAYMENT_REQUIRED);

        } else {
            logger.info("Payment response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

}
