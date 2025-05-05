package com.elusivemel.inventoryservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elusivemel.inventoryservice.dto.InventoryRequest;
import com.elusivemel.inventoryservice.dto.InventoryResponse;
import com.elusivemel.inventoryservice.dto.ReleaseInventoryRequest;
import com.elusivemel.inventoryservice.service.InventoryService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LogManager.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> checkInventory(@RequestBody InventoryRequest inventoryRequest) {

        InventoryResponse response = new InventoryResponse();
        try {
            response = inventoryService.checkInventory(inventoryRequest);
        } catch (EntityNotFoundException entityNotFoundException) {
            logger.error("Error: {}", entityNotFoundException.getMessage());
            response.setOrderId(inventoryRequest.getOrderId());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/release")
    public ResponseEntity<InventoryResponse> releaseInventory(@RequestBody ReleaseInventoryRequest releaseInventoryRequest) {

        InventoryResponse response = inventoryService.releaseInventory(releaseInventoryRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
