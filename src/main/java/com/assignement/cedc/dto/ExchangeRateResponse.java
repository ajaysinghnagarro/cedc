package com.assignement.cedc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeRateResponse {

    @JsonProperty("base_code")
    private String base;

    @JsonProperty("conversion_rates")
    private Map<String, Double> rates;

    // Getters and Setters
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
