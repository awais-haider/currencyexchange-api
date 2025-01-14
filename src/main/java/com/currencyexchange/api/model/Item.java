package com.currencyexchange.api.model;

import jakarta.validation.constraints.NotNull;

public record Item(
        String name,
        String category,
        double price
) {}