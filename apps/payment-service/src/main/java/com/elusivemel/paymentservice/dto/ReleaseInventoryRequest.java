package com.elusivemel.paymentservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.ToString;

@ToString
public class ReleaseInventoryRequest {

    private long orderId;

    private List<ReleaseInventoryRequestItem> items;

    public ReleaseInventoryRequest(PaymentRequest paymentRequest) {
        this.orderId = paymentRequest.getOrderId();

        this.items = paymentRequest.getItems().stream()
                .map(ReleaseInventoryRequestItem::new)
                .collect(Collectors.toList());
    }

    public ReleaseInventoryRequest() {
        this.items = new ArrayList<>();
    }

    public List<ReleaseInventoryRequestItem> getItems() {
        return items;
    }

    public void setItems(List<ReleaseInventoryRequestItem> items) {
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

}
