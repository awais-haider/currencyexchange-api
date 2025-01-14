// src/main/java/com/currencyexchange/api/service/DiscountServiceImpl.java
package com.currencyexchange.api.service.impl;

import com.currencyexchange.api.model.Bill;
import com.currencyexchange.api.model.Item;
import com.currencyexchange.api.model.User;
import com.currencyexchange.api.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Service to calculate discount on the bill
 */
@Service
public class DiscountServiceImpl implements DiscountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Override
    public double calculateDiscount(Bill bill) {
        LOGGER.info("Calculating discount for bill: {}", bill);
        double discount = 0;
        User user = bill.user();
        double totalAmount = bill.totalAmount();

        if (user.type().equalsIgnoreCase("employee")) {
            LOGGER.debug("User is an employee, applying 30% discount");
            discount = 0.30;
        } else if (user.type().equalsIgnoreCase("affiliate")) {
            LOGGER.debug("User is an affiliate, applying 10% discount");
            discount = 0.10;
        } else if (user.tenure() > 2) {
            LOGGER.debug("User has tenure greater than 2 years, applying 5% discount");
            discount = 0.05;
        }

        double percentageDiscount = 0;
        for (Item item : bill.items()) {
            if (!item.category().equalsIgnoreCase("groceries")) {
                percentageDiscount += item.price() * discount;
            }
        }

        double additionalDiscount = (int) (totalAmount / 100) * 5.0;
        double  finalAmount =  totalAmount - percentageDiscount - additionalDiscount;
        LOGGER.info("Final amount after discount: {}", finalAmount);
        return finalAmount;
    }
}