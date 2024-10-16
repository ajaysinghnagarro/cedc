package com.assignement.cedc.service;

import com.assignement.cedc.dto.ExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyExchangeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;

    private String baseCurrency = "USD";
    private String targetCurrency = "EUR";
    private double amount = 100.0;
    private double exchangeRate = 0.85; // For example, 1 USD = 0.85 EUR

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(currencyExchangeService, "exchangeEndpoint", "https://mocked-endpoint.com%s");
    }

    @Test
    public void testGetExchangeRate() {
        // Arrange
        ExchangeRateResponse mockResponse = mock(ExchangeRateResponse.class);
        when(mockResponse.getRates()).thenReturn(Map.of(targetCurrency, exchangeRate));
        ResponseEntity<ExchangeRateResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.getForEntity(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(responseEntity);

        double rate = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        assertEquals(exchangeRate, rate, "The exchange rate should be 0.85");
    }

    @Test
    public void testConvertCurrency() {
        // Arrange
        ExchangeRateResponse mockResponse = mock(ExchangeRateResponse.class);
        when(mockResponse.getRates()).thenReturn(Map.of(targetCurrency, exchangeRate));
        ResponseEntity<ExchangeRateResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.getForEntity(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(responseEntity);

        double convertedAmount = currencyExchangeService.convertCurrency(amount, baseCurrency, targetCurrency);

        double expectedAmount = amount * exchangeRate;
        assertEquals(expectedAmount, convertedAmount, "The converted amount should be 85.0 EUR");
    }

    @Test
    public void testConvertCurrency_WithDifferentExchangeRate() {
        // Arrange
        double newExchangeRate = 1.25; // For example, 1 USD = 1.25 GBP
        ExchangeRateResponse mockResponse = mock(ExchangeRateResponse.class);
        when(mockResponse.getRates()).thenReturn(Map.of("GBP", newExchangeRate));
        ResponseEntity<ExchangeRateResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.getForEntity(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(responseEntity);

        double convertedAmount = currencyExchangeService.convertCurrency(amount, baseCurrency, "GBP");

        double expectedAmount = amount * newExchangeRate;
        assertEquals(expectedAmount, convertedAmount, "The converted amount should be 125.0 GBP");
    }

    @Test
    public void testGetExchangeRate_NoRateFound() {
        // Arrange
        ExchangeRateResponse mockResponse = mock(ExchangeRateResponse.class);
        when(mockResponse.getRates()).thenReturn(Map.of());
        ResponseEntity<ExchangeRateResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.getForEntity(anyString(), eq(ExchangeRateResponse.class)))
                .thenReturn(responseEntity);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);
        }, "Expected exception when no exchange rate is found");
    }


}
