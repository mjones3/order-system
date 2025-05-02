/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elusivemel.orderservice.dto;

import com.elusivemel.orderservice.model.OrderItem;

import lombok.ToString;

@ToString
class OrderResponseItem {

    private int id;
    private String productId;
    private int quantity;

    public OrderResponseItem(OrderItem item) {
        this.id = item.getId().intValue();
        this.productId = item.getProductId();
        this.quantity = item.getQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
