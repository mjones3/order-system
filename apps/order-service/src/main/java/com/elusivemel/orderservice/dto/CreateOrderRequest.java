package com.elusivemel.orderservice.dto;

import java.util.List;

import com.elusivemel.orderservice.model.OrderItem;

import lombok.ToString;

@ToString
public class CreateOrderRequest {

    public CreateOrderRequest() {
    }

    private List<OrderItem> items;

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
