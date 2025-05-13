package com.elusivemel.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.jakarta.servlet.AWSXRayServletFilter;   // new package

import jakarta.servlet.Filter;                                    // Jakarta API

@Configuration
public class XRayConfig {

    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter("InventoryService");
    }
}
