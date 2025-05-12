package com.elusivemel.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.xray.spring.aop.XRayEnabled;

@SpringBootApplication
@XRayEnabled  // Enable X-Ray for all controller methods
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
