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

@RestController
@RequestMapping("/api")
public class BillController {

    @Autowired
    private BillServiceImpl billServiceImpl;

    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculateBill(@RequestBody BillRequest request) {

        double payableAmountInTargetCurrency = billServiceImpl.calulatePaybleBill(request);

        return ResponseEntity.ok(new BillResponse(payableAmountInTargetCurrency));
    }

}
