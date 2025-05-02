package com.elusivemel.inventoryservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class InventoryResponse {

    private long orderId;

    private List<InventoryResponseItem> items;

    public InventoryResponse() {
        this.items = new ArrayList<>();
    }

    public List<InventoryResponseItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryResponseItem> items) {
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

}
