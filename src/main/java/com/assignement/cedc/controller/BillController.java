package com.assignement.cedc.controller;

import com.assignement.cedc.dto.BillRequest;
import com.assignement.cedc.dto.BillResponse;
import com.assignement.cedc.service.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillServiceImpl billServiceImpl;

    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculateBill(@RequestBody BillRequest request) {
        logger.info("Received request to calculate bill: {}", request);
        double payableAmountInTargetCurrency = billServiceImpl.calulatePaybleBill(request);
        logger.info("Returning calculated payable amount: {}", payableAmountInTargetCurrency);

        return ResponseEntity.ok(new BillResponse(payableAmountInTargetCurrency));
    }

}
