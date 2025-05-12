package com.elusivemel.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.AWSXRay;

@Configuration
public class XRayConfig {

    @Bean
    public void configureXRay() {
        // Enable X-Ray tracing globally
        AWSXRay.beginSegment("PaymentService");
    }
}
