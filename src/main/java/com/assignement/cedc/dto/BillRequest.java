package com.assignement.cedc.dto;

import java.util.List;

public class BillRequest {
    private List<Item> items;
    private double totalAmount;
    private String userType;

    public BillRequest(){}
    public BillRequest(List<Item> items, String userType, int customerYears, String originalCurrency, String targetCurrency) {
        this.items = items;
        this.userType = userType;
        this.customerYears = customerYears;
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
    }

    private int customerYears;
    private String originalCurrency;
    private String targetCurrency;

    // Getters and Setters
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCustomerYears() {
        return customerYears;
    }

    public void setCustomerYears(int customerYears) {
        this.customerYears = customerYears;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
