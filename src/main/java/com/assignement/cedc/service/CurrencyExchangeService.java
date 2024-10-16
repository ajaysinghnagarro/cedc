package com.assignement.cedc.service;

public interface CurrencyExchangeService {

    public double getExchangeRate(String baseCurrency, String targetCurrency) ;

    public double convertCurrency(double amount, String baseCurrency, String targetCurrency);
}
