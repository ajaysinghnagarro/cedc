package com.assignement.cedc.service;

import com.assignement.cedc.dto.BillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Override
    public double calulatePaybleBill(BillRequest request) {
        double paybleAmountInOriginalCurrency = discountService.applyDiscounts(request);
        double payableAmountInTargetCurrency = currencyExchangeService.convertCurrency(
                paybleAmountInOriginalCurrency,
                request.getOriginalCurrency(),
                request.getTargetCurrency()
        );

        return payableAmountInTargetCurrency;
    }


}
