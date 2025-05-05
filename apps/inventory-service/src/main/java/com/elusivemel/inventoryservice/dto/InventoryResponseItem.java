package com.elusivemel.inventoryservice.dto;

import java.math.BigDecimal;

import com.elusivemel.inventoryservice.model.Inventory;

import lombok.ToString;

@ToString
public class InventoryResponseItem {

    private long orderItemId;
    private String productId;
    private int availableQuantity;
    private BigDecimal price;

    public InventoryResponseItem(InventoryRequestItem requestItem) {
        this.orderItemId = requestItem.getOrderItemId();
        this.availableQuantity = requestItem.getDesiredQuantity();
        this.productId = requestItem.getProductId();
        this.price = new BigDecimal(0);
    }

    public InventoryResponseItem(Inventory inventoryItem) {
        this.availableQuantity = inventoryItem.getRemainingQuantity();
        this.productId = inventoryItem.getProductId();
        this.price = inventoryItem.getPrice();
    }

    public InventoryResponseItem() {
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
