/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elusivemel.orderservice.dto;

import com.elusivemel.orderservice.model.OrderItem;

import lombok.ToString;

@ToString
class OrderResponseItem {

    private int orderItemId;
    private String productId;
    private int desiredQuantity;

    public OrderResponseItem(OrderItem item) {
        this.orderItemId = item.getId().intValue();
        this.productId = item.getProductId();
        this.desiredQuantity = item.getQuantity();
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

}
