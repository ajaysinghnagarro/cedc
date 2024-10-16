package com.assignement.cedc.service;

import com.assignement.cedc.dto.BillRequest;
import com.assignement.cedc.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountServiceImplTest {

    private DiscountServiceImpl discountService;

    @BeforeEach
    public void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void testApplyDiscounts_EmployeeDiscount() {
        List<Item> items = Arrays.asList(
                new Item("Apple", "groceries", 100),
                new Item("Laptop", "electronics", 1000)
        );
        BillRequest request = new BillRequest(items, "employee", 3, "USD", "EUR");
        double finalAmount = discountService.applyDiscounts(request);
        // Employee discount: 30% on non-grocery items (Laptop)
        double expectedAmount = 100 + 1000 - (0.30 * 1000) - (((100 + 1000)/100.0)* 5);
        assertEquals(expectedAmount, finalAmount, 0.01);
    }

    @Test
    public void testApplyDiscounts_AffiliateDiscount() {
        // Arrange
        List<Item> items = Arrays.asList(
                new Item("Apple", "groceries", 100),
                new Item("TV", "electronics", 500)
        );
        BillRequest request = new BillRequest(items, "affiliate", 1, "USD", "EUR");

        double finalAmount = discountService.applyDiscounts(request);

        double expectedAmount = 100 + 500 - (0.10 * 500) - (((100 + 500)/100.0)* 5); // $500 * 10% = $50, $5 flat discount
        assertEquals(expectedAmount, finalAmount, 0.01);
    }

    @Test
    public void testApplyDiscounts_CustomerOver2YearsDiscount() {
        // Arrange
        List<Item> items = Arrays.asList(
                new Item("Apple", "groceries", 100),
                new Item("Laptop", "electronics", 1000)
        );
        BillRequest request = new BillRequest(items, "customer", 3, "USD", "EUR");

        // Act
        double finalAmount = discountService.applyDiscounts(request);
        double expectedAmount = 100 + 1000 - (0.05 * 1000) - (((100 + 1000)/100.0)* 5);
        assertEquals(expectedAmount, finalAmount, 0.01);
    }

    @Test
    public void testApplyDiscounts_NoDiscountForNewCustomer() {
        List<Item> items = Arrays.asList(
                new Item("Apple", "groceries", 100),
                new Item("TV", "electronics", 500)
        );
        BillRequest request = new BillRequest(items, "customer", 1, "USD", "EUR");
        double finalAmount = discountService.applyDiscounts(request);
        double expectedAmount = 100 + 500 - (((100 + 500)/100.0)* 5);
        assertEquals(expectedAmount, finalAmount, 0.01);
    }

    @Test
    public void testApplyDiscounts_WithOnlyGroceryItems() {
        // Arrange
        List<Item> items = Arrays.asList(
                new Item("Apple", "groceries", 100),
                new Item("Banana", "groceries", 50)
        );
        BillRequest request = new BillRequest(items, "employee", 5, "USD", "EUR");

        double finalAmount = discountService.applyDiscounts(request);
        double expectedAmount = 100 + 50 - (((100 + 50)/100.0)* 5); // Flat discount for $5 every $100
        assertEquals(expectedAmount, finalAmount);
    }
}
