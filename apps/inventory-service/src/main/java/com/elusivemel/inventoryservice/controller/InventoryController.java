package com.elusivemel.inventoryservice.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

import com.elusivemel.inventoryservice.dto.InventoryRequest;
import com.elusivemel.inventoryservice.dto.InventoryRequestItem;
import com.elusivemel.inventoryservice.dto.InventoryResponse;
import com.elusivemel.inventoryservice.dto.InventoryResponseItem;
import com.elusivemel.inventoryservice.model.Inventory;
import com.elusivemel.inventoryservice.repository.InventoryRepository;
import com.elusivemel.inventoryservice.util.InventoryUtil;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger logger = LogManager.getLogger(InventoryController.class);

    @Autowired
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> checkInventory(@RequestBody InventoryRequest inventoryRequest) {
        List<InventoryRequestItem> inventoryRequestItems = inventoryRequest.getItems();

        List<InventoryResponseItem> responseItemsList = inventoryRequestItems.stream()
                .map(InventoryResponseItem::new)
                .collect(Collectors.toList());

        InventoryResponse response = new InventoryResponse();
        response.setOrderId(inventoryRequest.getOrderId());
        response.setItems(responseItemsList);
        response.setTotal(new BigDecimal(0));

        try {

            responseItemsList = inventoryRequestItems.stream()
                    .map(i -> {

                        Inventory inventoryItem = inventoryRepository.findByProductId(i.getProductId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                "No inventory for product " + i.getProductId()));

                        InventoryResponseItem responseItem = new InventoryResponseItem();
                        responseItem.setOrderItemId(i.getOrderItemId());
                        responseItem.setProductId(i.getProductId());
                        responseItem.setAvailableQuantity(inventoryItem.getQuantity());
                        responseItem.setPrice(inventoryItem.getPrice());

                        boolean hasEnoughInventory = InventoryUtil.isInventoryAvailable(i.getDesiredQuantity(), inventoryItem.getQuantity());

                        if (hasEnoughInventory) {
                            logger.info("Inventory available for product {} with desired quantity {} and available quantity {}. Decreasing by desired quantity",
                                    i.getProductId(), i.getDesiredQuantity(), inventoryItem.getQuantity());

                            inventoryItem.setRemainingQuantity(inventoryItem.getQuantity() - i.getDesiredQuantity());
                            responseItem.setAvailableQuantity(inventoryItem.getRemainingQuantity());
                            inventoryRepository.save(inventoryItem);
                        } else {
                            logger.error("Not enough inventory for product {} with desired quantity {} and available quantity {}",
                                    i.getProductId(), i.getDesiredQuantity(), inventoryItem.getQuantity());
                            throw new EntityNotFoundException("Not enough inventory for product " + i.getProductId());

                        }

                        return responseItem;
                    }).collect(Collectors.toList());

            response.setItems(responseItemsList);
            response.setOrderId(inventoryRequest.getOrderId());

            Map<String, BigDecimal> priceByProductId = responseItemsList.stream()
                    .filter(r -> r.getPrice() != null)
                    .collect(Collectors.toMap(
                            InventoryResponseItem::getProductId,
                            InventoryResponseItem::getPrice
                    ));

            BigDecimal total = inventoryRequestItems.stream()
                    .filter(req -> priceByProductId.containsKey(req.getProductId()))
                    .map(req -> {
                        BigDecimal price = priceByProductId.get(req.getProductId());
                        BigDecimal quantity = BigDecimal.valueOf(req.getDesiredQuantity());
                        return price.multiply(quantity);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            response.setTotal(total);
            logger.info("Inventory response: {}", response);

        } catch (EntityNotFoundException entityNotFoundException) {
            logger.error("Error: {}", entityNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
