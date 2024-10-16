package com.assignement.cedc.service;

import com.assignement.cedc.dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${exchange.api.conversion.endpoint}")
    private String exchangeEndpoint;

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = String.format(exchangeEndpoint, baseCurrency);
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
        return response.getBody().getRates().get(targetCurrency);
    }

    public double convertCurrency(double amount, String baseCurrency, String targetCurrency) {
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
        return amount * exchangeRate;
    }
}
