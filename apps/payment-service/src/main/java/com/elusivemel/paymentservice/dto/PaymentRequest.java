package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class PaymentRequest {

    private long orderId;

    private List<PaymentRequestItem> items;

    private BigDecimal total;

    public PaymentRequest() {
        this.items = new ArrayList<>();
    }

    public List<PaymentRequestItem> getItems() {
        return items;
    }

    public void setItems(List<PaymentRequestItem> items) {
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
