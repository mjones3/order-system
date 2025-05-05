package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;

import lombok.ToString;

@ToString
public class PaymentResponseItem {

    private long orderItemId;
    private String productId;
    private int availableQuantity;
    private int desiredQuantity;

    private BigDecimal price;

    public PaymentResponseItem(PaymentRequestItem item) {
        this.orderItemId = item.getOrderItemId();
        this.productId = item.getProductId();
        this.desiredQuantity = item.getDesiredQuantity();
        this.availableQuantity = item.getAvailableQuantity();
        this.price = item.getPrice().multiply(new BigDecimal(item.getDesiredQuantity()));
    }

    public PaymentResponseItem() {
    }

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

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

}
