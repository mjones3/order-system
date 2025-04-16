package com.elusivemel.inventoryservice.controller;

import com.elusivemel.inventoryservice.model.Inventory;
import com.elusivemel.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LogManager.getLogger(InventoryController.class);
    String json = "{}";

    @Autowired
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public ResponseEntity<Inventory> createOrder(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        logger.info(savedInventory);

        return new ResponseEntity<Inventory>(savedInventory, HttpStatus.CREATED);
    }
}
