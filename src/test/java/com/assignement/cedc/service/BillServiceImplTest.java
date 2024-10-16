package com.assignement.cedc.service;

import com.assignement.cedc.dto.BillRequest;
import com.assignement.cedc.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BillServiceImplTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private BillServiceImpl billService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalulatePaybleBill() {
        BillRequest request = new BillRequest();
        request.setItems(Arrays.asList(
                new Item("Laptop", "electronics", 1000),
                new Item("Apple", "groceries", 50)
        ));
        request.setUserType("employee");
        request.setCustomerYears(3);
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");

        when(discountService.applyDiscounts(request)).thenReturn(950.0);
        when(currencyExchangeService.convertCurrency(950.0, "USD", "EUR")).thenReturn(850.0);
        double result = billService.calulatePaybleBill(request);
        assertEquals(850.0, result);
        verify(discountService, times(1)).applyDiscounts(request);
        verify(currencyExchangeService, times(1)).convertCurrency(950.0, "USD", "EUR");
    }

    @Test
    public void testCalulatePaybleBill_NoDiscounts() {
        // Arrange
        BillRequest request = new BillRequest();
        request.setItems(Arrays.asList(
                new Item("Tablet", "electronics", 500),
                new Item("Banana", "groceries", 100)
        ));
        request.setUserType("affiliate");
        request.setCustomerYears(1);
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("GBP");

        when(discountService.applyDiscounts(request)).thenReturn(575.0);
        when(currencyExchangeService.convertCurrency(575.0, "USD", "GBP")).thenReturn(500.0);  // Mocked conversion

        double result = billService.calulatePaybleBill(request);

        assertEquals(500.0, result);

        verify(discountService, times(1)).applyDiscounts(request);
        verify(currencyExchangeService, times(1)).convertCurrency(575.0, "USD", "GBP");
    }
}
