package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.elusivemel.paymentservice.model.Payment;

import lombok.ToString;

@ToString
public class PaymentResponse {

    private long orderId;
    private BigDecimal orderTotal;
    private String approved;

    private List<PaymentResponseItem> items;

    public PaymentResponse() {
        this.items = new ArrayList<>();
    }

    public PaymentResponse(List<PaymentResponseItem> items, long orderId, BigDecimal total) {
        this.items = items;
        this.orderId = orderId;
    }

    public PaymentResponse(Payment payment) {
        this.orderId = payment.getOrderId();
        this.orderTotal = payment.getTotal();
        this.approved = payment.getStatus();
    }

    public List<PaymentResponseItem> getItems() {
        return items;
    }

    public void setItems(List<PaymentResponseItem> items) {
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

}
