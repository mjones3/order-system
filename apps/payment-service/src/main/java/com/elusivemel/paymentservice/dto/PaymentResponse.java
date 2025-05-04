package com.elusivemel.paymentservice.dto;

import java.math.BigDecimal;

import com.elusivemel.paymentservice.model.Payment;

import lombok.ToString;

@ToString
public class PaymentResponse {

    private Long orderId;
    private BigDecimal orderTotal;
    private String approved;

    public PaymentResponse(Payment payment) {
        this.orderId = payment.getOrderId();
        this.orderTotal = payment.getTotal();
        this.approved = payment.getStatus();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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
