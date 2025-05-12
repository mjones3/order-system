package com.elusivemel.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.xray.spring.aop.XRayEnabled;

@SpringBootApplication
@XRayEnabled
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
