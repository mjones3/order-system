package com.elusivemel.inventoryservice.dto;

public class ReleaseInventoryRequestItem {

    private long orderItemId;
    private String productId;
    private int releaseQuantity;

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

}
