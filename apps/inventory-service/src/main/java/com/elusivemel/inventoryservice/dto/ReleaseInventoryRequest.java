package com.elusivemel.inventoryservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class ReleaseInventoryRequest {

    private long orderId;

    private List<ReleaseInventoryRequestItem> items;

    public ReleaseInventoryRequest() {
        this.items = new ArrayList<>();
    }

    public List<ReleaseInventoryRequestItem> getItems() {
        return items;
    }

    public void setItems(List<ReleaseInventoryRequestItem> items) {
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

}
