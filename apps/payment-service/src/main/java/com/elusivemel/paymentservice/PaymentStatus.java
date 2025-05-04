package com.elusivemel.paymentservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    /**
     * Returns the String value for this enum
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentStatus fromValue(String value) {
        return fromString(value);
    }

    /**
     * toString() now returns the String value
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Parse a String into an PaymentStatus, case‐insensitive.
     *
     * @throws IllegalArgumentException if no match found
     */
    public static PaymentStatus fromString(String text) {
        for (PaymentStatus s : PaymentStatus.values()) {
            if (s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown PaymentStatus: " + text);
    }

}
