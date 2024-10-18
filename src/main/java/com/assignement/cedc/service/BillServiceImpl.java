package com.assignement.cedc.service;

import com.assignement.cedc.controller.BillController;
import com.assignement.cedc.dto.BillRequest;
import com.assignement.cedc.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        convertBillIntoTargetCurrencyFormat(request);
        double payableAmountInTargetCurrency = discountService.applyDiscounts(request);
        logger.info("Payable amount after applying discount in Target currency {} {}",payableAmountInTargetCurrency,request.getTargetCurrency());
        return payableAmountInTargetCurrency;
    }

    public void convertBillIntoTargetCurrencyFormat(BillRequest request) {
        logger.debug("Started converting bill amounts into target currency for the request {}", request);
        double exchangeRate = currencyExchangeService.getExchangeRate(request.getOriginalCurrency(), request.getTargetCurrency());
        logger.debug("Converting all bill amounts into target currency with exchange rate {}", exchangeRate);
        List<Item> items = request.getItems();
        items.forEach(item -> item.setPrice(item.getPrice() * exchangeRate));
        request.setTotalAmount(request.getTotalAmount() * exchangeRate);
        logger.debug("End converting bill amounts into target currency total amount: {}", request.getTotalAmount());

    }



}
