package com.currencyexchange.api.controller;

import com.currencyexchange.api.model.Bill;
import com.currencyexchange.api.model.CalculatedDiscountResponse;
import com.currencyexchange.api.service.DiscountService;
import com.currencyexchange.api.service.ExchangeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Validated
@OpenAPIDefinition(info = @Info(title = "Currency Exchange API", version = "1.0", description = "API to calculate payable amount after discount and currency conversion"))
@RestController
@RequestMapping("/api")
public class CalculationController {

    private final DiscountService discountService;
    private final ExchangeService exchangeService;

    @Autowired
    public CalculationController(DiscountService discountService, ExchangeService exchangeService) {
        this.discountService = discountService;
        this.exchangeService = exchangeService;
    }

    @Operation(summary = "Calculate the final payable amount after applying discounts and currency conversion")
    @PostMapping("/calculate")
    public Mono<ResponseEntity<CalculatedDiscountResponse>>calculatePayableAmount(@RequestBody @Valid Bill bill) {
        double discountedAmount = discountService.calculateDiscount(bill);
        return exchangeService.convertCurrency(discountedAmount, bill.originalCurrency(), bill.targetCurrency())
                .map(convertedAmount -> {

                    double formattedAmount = Double.parseDouble(String.format("%.3f", convertedAmount));
                    return ResponseEntity.ok(new CalculatedDiscountResponse(formattedAmount));

                });
    }
}