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
        request.setTotalAmount(1050);

        when(discountService.applyDiscounts(request)).thenReturn(850.0);
        when(currencyExchangeService.getExchangeRate( "USD", "EUR")).thenReturn(0.918);

        double result = billService.calulatePaybleBill(request);
        assertEquals(850.0, result);
        verify(discountService, times(1)).applyDiscounts(request);
        verify(currencyExchangeService, times(1)).getExchangeRate( "USD", "EUR");
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
        request.setTotalAmount(600);

        when(discountService.applyDiscounts(request)).thenReturn(500.0);
        when(currencyExchangeService.getExchangeRate( "USD", "GBP")).thenReturn(0.765);  // Mocked conversion

        double result = billService.calulatePaybleBill(request);

        assertEquals(500.0, result);

        verify(discountService, times(1)).applyDiscounts(request);
        verify(currencyExchangeService, times(1)).getExchangeRate( "USD", "GBP");
    }

    @Test
    void testConvertBillIntoTargetCurrencyFormat() {
        BillRequest request = new BillRequest();
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");
        request.setTotalAmount(150.0);

        Item item1 = new Item("Apple", "groceries", 50);
        Item item2 = new Item("Laptop", "electronics", 100);
        request.setItems(Arrays.asList(item1, item2));

        double exchangeRate = 0.85;
        when(currencyExchangeService.getExchangeRate("USD", "EUR")).thenReturn(exchangeRate);
        billService.convertBillIntoTargetCurrencyFormat(request);
        assertEquals(42.5, item1.getPrice()); // 50 * 0.85
        assertEquals(85.0, item2.getPrice()); // 100 * 0.85
        assertEquals(127.5, request.getTotalAmount()); // 150 * 0.85
        verify(currencyExchangeService, times(1)).getExchangeRate("USD", "EUR");
    }
}
