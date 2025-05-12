package com.elusivemel.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.amazonaws.xray.spring.aop.XRayEnabled;

@SpringBootApplication
@XRayEnabled
@EntityScan("com.elusivemel.orderservice.model")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
