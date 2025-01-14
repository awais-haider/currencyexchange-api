package com.currencyexchange.api.service;

import com.currencyexchange.api.model.Bill;
import org.springframework.boot.SpringApplication;

public interface DiscountService {
     double calculateDiscount(Bill bill);
}
