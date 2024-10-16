package com.assignement.cedc.dto;

public class BillResponse {
    private double payableAmount;

    public BillResponse(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    // Getter and Setter
    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }
}
