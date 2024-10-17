package com.assignement.cedc.service;

import com.assignement.cedc.controller.BillController;
import com.assignement.cedc.dto.BillRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private DiscountService discountService;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Override
    public double calulatePaybleBill(BillRequest request) {
        logger.info("Started Calculating payable bill ");
        double paybleAmountInOriginalCurrency = discountService.applyDiscounts(request);
        logger.info("Payable amount after applying discount in original currency {} {}",paybleAmountInOriginalCurrency,request.getOriginalCurrency());

        double payableAmountInTargetCurrency = currencyExchangeService.convertCurrency(
                paybleAmountInOriginalCurrency,
                request.getOriginalCurrency(),
                request.getTargetCurrency()
        );
        logger.info("Payable amount after conversion in target currency {} {}",payableAmountInTargetCurrency,request.getTargetCurrency());

        return payableAmountInTargetCurrency;
    }


}
