package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;

public class PaymentRequestItem {

    private long orderItemId;
    private String productId;
    private int desiredQuantity;
    private int availableQuantity;
    private BigDecimal price;

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

}
