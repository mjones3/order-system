package com.elusivemel.inventoryservice.dto;

import lombok.ToString;

@ToString
public class InventoryRequestItem {

    private long orderId;
    private long orderItemId;
    private String productId;
    private int desiredQuantity;

    public long getOrderId() {
        return orderId;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

}
