package com.elusivemel.inventoryservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elusivemel.inventoryservice.dto.InventoryRequest;
import com.elusivemel.inventoryservice.dto.InventoryRequestItem;
import com.elusivemel.inventoryservice.dto.InventoryResponse;
import com.elusivemel.inventoryservice.dto.InventoryResponseItem;
import com.elusivemel.inventoryservice.dto.ReleaseInventoryRequest;
import com.elusivemel.inventoryservice.model.Inventory;
import com.elusivemel.inventoryservice.repository.InventoryRepository;
import com.elusivemel.inventoryservice.util.InventoryUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    private static final Logger logger = LogManager.getLogger(InventoryService.class);

    public InventoryResponse checkInventory(InventoryRequest inventoryRequest) throws EntityNotFoundException {

        List<InventoryRequestItem> inventoryRequestItems = inventoryRequest.getItems();

        InventoryResponse response = new InventoryResponse();
        response.setOrderId(inventoryRequest.getOrderId());

        List<InventoryResponseItem> responseItemsList = inventoryRequestItems.stream()
                .map(i -> {

                    Inventory inventoryItem = inventoryRepository.findByProductId(i.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException(
                            "No inventory for product " + i.getProductId()));

                    InventoryResponseItem responseItem = new InventoryResponseItem();
                    responseItem.setOrderItemId(i.getOrderItemId());
                    responseItem.setProductId(i.getProductId());
                    responseItem.setAvailableQuantity(inventoryItem.getQuantity());
                    responseItem.setDesiredQuantity(i.getDesiredQuantity());
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

        return response;
    }

    public List<InventoryResponseItem> buildResponseItems(List<InventoryRequestItem> requestItems) {
        return requestItems.stream()
                .map(reqItem -> {
                    // 1) Look up the Inventory entity by productId
                    Inventory inv = inventoryRepository
                            .findByProductId(reqItem.getProductId())
                            .orElseThrow(() -> new NoSuchElementException(
                            "No inventory found for productId=" + reqItem.getProductId()
                    ));

                    // 2) Create your response DTO combining request quantity + DB price
                    return new InventoryResponseItem(
                            inv.getProductId(),
                            inv.getPrice(), // price from DB
                            reqItem
                    );
                })
                .collect(Collectors.toList());
    }

    public InventoryResponse releaseInventory(ReleaseInventoryRequest releaseInventoryRequest) {
        InventoryResponse response = new InventoryResponse();

        List<InventoryResponseItem> inventoryResponseItems = releaseInventoryRequest.getItems().stream()
                .map(itemReq -> {
                    Inventory inv = inventoryRepository.findByProductId(itemReq.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException(
                            "No inventory for " + itemReq.getProductId()));

                    int delta = itemReq.getReleaseQuantity();
                    inv.setRemainingQuantity(inv.getRemainingQuantity() - delta);
                    Inventory updated = inventoryRepository.save(inv);
                    return new InventoryResponseItem(updated);
                })
                .collect(Collectors.toList());

        response.setItems(inventoryResponseItems);
        response.setOrderId(releaseInventoryRequest.getOrderId());

        return response;
    }

    public void setInventoryRepository(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

}
