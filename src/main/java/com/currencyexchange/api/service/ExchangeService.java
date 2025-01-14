package com.currencyexchange.api.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface ExchangeService {
    Mono<Map<String, Double>> getExchangeRates(String baseCurrency);

    Mono<Double> convertCurrency(double amount, String originalCurrency, String targetCurrency);
}
