package com.elusivemel.inventoryservice.util;

public class InventoryUtil {

    public static boolean isInventoryAvailable(int requestedQuantity, int availableQuantity) {
        return requestedQuantity <= availableQuantity;
    }
}
