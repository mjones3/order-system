package com.elusivemel.inventoryservice.dto;

import java.util.List;

import lombok.ToString;

@ToString
public class InventoryRequest {

    public InventoryRequest() {
    }

    private long orderId;

    private List<InventoryRequestItem> items;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<InventoryRequestItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryRequestItem> items) {
        this.items = items;
    }

}
