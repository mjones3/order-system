package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;

public class PaymentRequestItems {

    private long orderItemId;
    private String productId;
    private int availableQuantity;
    private BigDecimal price;

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
