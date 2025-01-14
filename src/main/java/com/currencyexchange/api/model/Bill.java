package com.currencyexchange.api.model;


import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Represents a bill with items, total amount, user, original currency and target currency
 */
public record Bill(
        @NotNull List<Item> items,
        double totalAmount,
        @NotNull User user,
        @NotNull String originalCurrency,
        @NotNull String targetCurrency
) {
    public Bill {
        if (items == null) throw new IllegalArgumentException("items must not be null");
        if (user == null) throw new IllegalArgumentException("user must not be null");
        if (originalCurrency == null) throw new IllegalArgumentException("originalCurrency must not be null");
        if (targetCurrency == null) throw new IllegalArgumentException("targetCurrency must not be null");
    }
}