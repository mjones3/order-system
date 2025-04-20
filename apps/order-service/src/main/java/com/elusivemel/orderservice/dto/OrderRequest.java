package com.elusivemel.orderservice.dto;

import java.util.List;

import com.elusivemel.orderservice.model.OrderItem;

import lombok.ToString;

@ToString
public class OrderRequest {

    public OrderRequest() {
    }

    private List<OrderItemRequest> items;

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
