package com.elusivemel.paymentservice.dto;

public class ReleaseInventoryRequestItem {

    private long orderItemId;
    private String productId;
    private int releaseQuantity;
    private int availableQuantity;

    public ReleaseInventoryRequestItem(PaymentRequestItem paymentRequestItem) {
        this.releaseQuantity = paymentRequestItem.getDesiredQuantity();
        this.orderItemId = paymentRequestItem.getOrderItemId();
        this.productId = paymentRequestItem.getProductId();
        this.availableQuantity = paymentRequestItem.getAvailableQuantity();
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public int getReleaseQuantity() {
        return releaseQuantity;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setReleaseQuantity(int availableQuantity) {
        this.releaseQuantity = availableQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

}
