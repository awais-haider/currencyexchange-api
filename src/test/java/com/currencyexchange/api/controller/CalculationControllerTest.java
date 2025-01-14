package com.currencyexchange.api.controller;

import com.currencyexchange.api.model.Bill;
import com.currencyexchange.api.model.CalculatedDiscountResponse;
import com.currencyexchange.api.model.Item;
import com.currencyexchange.api.model.User;
import com.currencyexchange.api.service.DiscountService;
import com.currencyexchange.api.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculationControllerTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private CalculationController calculationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculatePayableAmountReturnsCorrectResponse() {

        List<Item> items = Arrays.asList(
                new Item("Electronics", "Electronics", 299.99),
                new Item("Books", "Books",19.99)
        );
        User user = new User("Regular", 5);
        Bill bill = new Bill(items, 319.98, user, "USD", "EUR");

        when(discountService.calculateDiscount(any(Bill.class))).thenReturn(90.0);
        when(exchangeService.convertCurrency(90.0, "USD", "EUR")).thenReturn(Mono.just(80.0));

        Mono<ResponseEntity<CalculatedDiscountResponse>> response = calculationController.calculatePayableAmount(bill);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getBody().finalPayableAmount() == 80.0)
                .verifyComplete();
    }


    @Test
    void calculatePayableAmountHandlesZeroDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Electronics", "Electronics", 299.99),
                new Item("Books", "Books",19.99)
        );

        User user = new User("Regular", 5);

        Bill bill = new Bill(items, 319.98, user, "USD", "EUR");
        when(discountService.calculateDiscount(any(Bill.class))).thenReturn(0.0);
        when(exchangeService.convertCurrency(0.0, "USD", "EUR")).thenReturn(Mono.just(0.0));

        Mono<ResponseEntity<CalculatedDiscountResponse>> response = calculationController.calculatePayableAmount(bill);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getBody().finalPayableAmount() == 0.0)
                .verifyComplete();
    }
}