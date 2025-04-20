package com.elusivemel.orderservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    PAID("PAID");

    private final String value;

    OrderStatus(String value) {
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
    public static OrderStatus fromValue(String value) {
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
     * Parse a String into an OrderStatus, case‐insensitive.
     *
     * @throws IllegalArgumentException if no match found
     */
    public static OrderStatus fromString(String text) {
        for (OrderStatus s : OrderStatus.values()) {
            if (s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown OrderStatus: " + text);
    }

}
