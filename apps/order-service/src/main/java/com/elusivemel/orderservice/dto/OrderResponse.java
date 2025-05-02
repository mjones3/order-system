package com.elusivemel.orderservice.dto;

import java.util.ArrayList;
import java.util.List;

import com.elusivemel.orderservice.model.Order;

import lombok.ToString;

@ToString
public class OrderResponse {

    private int orderId;
    private List<OrderResponseItem> items;

    public OrderResponse() {
        this.items = new ArrayList<>();
    }

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.items = order.getItems().stream().map(item -> new OrderResponseItem(item)).toList();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderResponseItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderResponseItem> items) {
        this.items = items;
    }

}
