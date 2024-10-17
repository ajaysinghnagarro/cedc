package com.assignement.cedc.service;

import com.assignement.cedc.dto.ExchangeRateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${exchange.api.conversion.endpoint}")
    private String exchangeEndpoint;

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        logger.debug("Starting to fetch exchange rate for base currency {} and target currency {}",baseCurrency,targetCurrency);

        String url = String.format(exchangeEndpoint, baseCurrency);
        logger.debug("Get request for the endpoint {}",url);
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
        logger.debug("Response from currency exchange service {}",response);

        return response.getBody().getRates().get(targetCurrency);
    }

    public double convertCurrency(double amount, String baseCurrency, String targetCurrency) {
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
        logger.info("Fetched exchange rate {}",exchangeRate);

        return amount * exchangeRate;
    }
}
