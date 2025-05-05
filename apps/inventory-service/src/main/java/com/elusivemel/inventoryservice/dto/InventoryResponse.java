package com.elusivemel.inventoryservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class InventoryResponse {

    private long orderId;

    private List<InventoryResponseItem> items;

    private BigDecimal total;

    public InventoryResponse() {
        this.items = new ArrayList<>();
    }

    public InventoryResponse(List<InventoryResponseItem> items, long orderId, BigDecimal total) {
        this.items = items;
        this.orderId = orderId;
        this.total = total;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
