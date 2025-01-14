// src/main/java/com/currencyexchange/api/service/ExchangeServiceImpl.java
package com.currencyexchange.api.service.impl;

import com.currencyexchange.api.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Service to get exchange rates and convert currency
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeServiceImpl.class);
    private final WebClient webClient;
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";
    @Value("${application.rest.currency-exchange.api-key}")
    private  String currencyApiKey;

    @Autowired
    public ExchangeServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }


    @Override
    public Mono<Map<String, Double>> getExchangeRates(String baseCurrency) {
        return webClient.get()
                .uri(currencyApiKey + "/latest/{baseCurrency}", baseCurrency)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    LOGGER.info("Exchange rates for {}: {}", baseCurrency, response.get("conversion_rates"));
                    return (Map<String, Double>) response.get("conversion_rates");
                });
    }

    @Override
    public Mono<Double> convertCurrency(double amount, String originalCurrency, String targetCurrency) {
        return getExchangeRates(originalCurrency)
                .flatMap(rates -> {
                    Number rate = rates.get(targetCurrency.toUpperCase());

                    if (rate == null) {
                        LOGGER.error("Issue while getting conversion rate for {} to {}", originalCurrency, targetCurrency);
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Issue while getting conversion}"));
                    }
                    return Mono.just(amount * rate.doubleValue());
                });
    }
}