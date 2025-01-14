package com.currencyexchange.api.service;

import com.currencyexchange.api.model.Bill;
import com.currencyexchange.api.model.Item;
import com.currencyexchange.api.model.User;
import com.currencyexchange.api.service.DiscountService;
import com.currencyexchange.api.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceImplTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    void calculatesDiscountForEmployee() {
        User user = new User("employee", 1);
        Item item1 = new Item("TV",  "electronics", 100);
        Item item2 = new Item("Flour", "groceries", 50);
        Bill bill = new Bill(Arrays.asList(item1, item2), 150.0, user, "USD", "EUR");

        double finalAmount = discountService.calculateDiscount(bill);

        assertEquals(115, finalAmount);
    }

    @Test
    void calculatesDiscountForAffiliate() {
        User user = new User("affiliate", 1);
        Item item1 = new Item("Fridge",  "electronics",  100);
        Item item2 = new Item("Salt", "groceries", 50);
        Bill bill = new Bill(Arrays.asList(item1, item2), 150.0, user, "USD", "EUR");

        double finalAmount = discountService.calculateDiscount(bill);

        assertEquals(135, finalAmount);
    }

    @Test
    void calculatesDiscountForLongTenureUser() {
        User user = new User("customer", 3);
        Item item1 = new Item("electronics",  "electronics", 100);
        Item item2 = new Item("groceries",  "groceries",50);
        Bill bill = new Bill(Arrays.asList(item1, item2), 150.0, user, "USD", "EUR");

        double finalAmount = discountService.calculateDiscount(bill);

        assertEquals(140, finalAmount);
    }

    @Test
    void calculatesDiscountForNonDiscountedUser() {
        User user = new User("customer", 1);
        Item item1 = new Item("Router",  "electronics",100);
        Item item2 = new Item("Snacks", "groceries",  50);
        Bill bill = new Bill(Arrays.asList(item1, item2), 150.0, user, "USD", "EUR");

        double finalAmount = discountService.calculateDiscount(bill);

        assertEquals(145, finalAmount);
    }

    @Test
    void calculatesDiscountForOnlyGroceries() {
        User user = new User("customer", 1);
        Item item1 = new Item("Laptop",  "electronics",100);
        Item item2 = new Item("Sweets", "groceries",  50);
        Bill bill = new Bill(Arrays.asList(item1, item2), 150.0, user, "USD", "EUR");

        double finalAmount = discountService.calculateDiscount(bill);

        assertEquals(145, finalAmount);
    }
}